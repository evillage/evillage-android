package nl.worth.clangnotifications

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.data.interactor.NotificationInteractor
import nl.worth.clangnotifications.data.interactor.PropertiesInteractor
import nl.worth.clangnotifications.data.interactor.TokenInteractor
import nl.worth.clangnotifications.data.model.ClangAccountResponse
import nl.worth.clangnotifications.data.network.ClangApiClient
import nl.worth.clangnotifications.util.getUserId
import java.lang.Exception

/**
 * This Class is used to log events to a remote server
 *
 * @param authenticationToken The authorization header value
 * @param integrationId PARAM DESCRIPTION GOES HERE
 */
@Keep
class ClangImplementation(
    baseUrl: String?,
    private var authenticationToken: String,
    private var integrationId: String
) : Clang() {

    lateinit var context: Context

    constructor(authenticationToken: String, integrationId: String) : this(
        null,
        authenticationToken,
        integrationId
    )

    init {
        ClangApiClient.init(baseUrl, authenticationToken)
    }

    /** Creates a unique user account using a unique device ID
     *
     * @param deviceId Unique device identifier
     * @param successCallback Notifies caller that action was successful returning an [ClangAccountResponse] object
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    override fun createAccount(
        deviceId: String,
        successCallback: (ClangAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveFirebaseToken(
            { token ->
                AccountInteractor().registerAccount(
                    context,
                    integrationId,
                    token,
                    deviceId,
                    successCallback,
                    errorCallback
                )
            },
            { throwable ->
                errorCallback(throwable)
            }
        )
    }

    /** Logs an event to remote server that may contain additional information
     * For example: event = "login" and data = "mapOf("action" to "login","email" to et_email.text.toString())"
     *
     * @param event The event to log
     * @param data The data of that event
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    override fun logEvent(
        event: String,
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val userId = context.getUserId()
        if (userId == null) {
            errorCallback(Throwable("User id is null, call createAccount() first passing a unique user id"))
            return
        }
        NotificationInteractor().logEvent(
            integrationId,
            event,
            data,
            context.getUserId()!!,
            successCallback,
            errorCallback
        )
    }

    /** METHOD DESCRIPTION GOES HERE
     *
     * @param data PARAM DESCRIPTION GOES HERE
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    override fun updateProperties(
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val userId = context.getUserId()
        if (userId == null) {
            errorCallback(Throwable("User id is null, call createAccount() first passing a unique user id"))
            return
        }
        PropertiesInteractor().updateProperties(
            integrationId,
            data,
            context.getUserId()!!,
            successCallback,
            errorCallback
        )
    }

    /** Logs a Notification
     *
     * @param actionId PARAM DESCRIPTION GOES HERE
     * @param notificationId PARAM DESCRIPTION GOES HERE
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    override fun logNotificationAction(
        actionId: String,
        notificationId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val userId = context.getUserId()
        if (userId == null) {
            errorCallback(Throwable("User id is null, call createAccount() first passing a unique user id"))
            return
        }
        NotificationInteractor().logNotificationAction(
            notificationId,
            context.getUserId()!!,
            actionId,
            successCallback,
            errorCallback
        )
    }

    /** Updates user's FCM token
     *
     * @param firebaseToken FCM token generated for this device
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    override fun updateToken(
        firebaseToken: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val userId = context.getUserId()
        if (userId == null) {
            errorCallback(Throwable("User id is null, call createAccount() first passing a unique user id"))
            return
        }
        TokenInteractor().sendTokenToServer(
            firebaseToken,
            userId,
            successCallback,
            errorCallback
        )
    }

    //region Util methods
    /**
     *
     * @param onTokenReceived Notifies caller that action was successful returning a FCM token
     */
    private fun retrieveFirebaseToken(
        onTokenReceived: (String) -> Unit,
        onTokenFailed: (Throwable) -> Unit
    ) {

        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if(result != null){
                var token = result
                token?.let { onTokenReceived(it) } ?: onTokenFailed(Exception("Null FCM token"))

            } else {

                onTokenFailed(Exception("Unknown FirebaseInstanceId exception"))

            }
        }
    }


    //endregion
}