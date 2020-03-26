package nl.worth.clangnotifications

import android.content.Context
import com.google.firebase.iid.FirebaseInstanceId
import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.data.interactor.NotificationInteractor
import nl.worth.clangnotifications.data.interactor.PropertiesInteractor
import nl.worth.clangnotifications.data.interactor.TokenInteractor
import nl.worth.clangnotifications.data.model.ClangAccountResponse
import nl.worth.clangnotifications.data.repository.ClangApiClient
import nl.worth.clangnotifications.util.getUserId

/**
 * This Class is used to log events to a remote server
 *
 * @param context Used to access
 * @param authenticationToken The authorization header value
 * @param integrationId PARAM DESCRIPTION GOES HERE
 */
class ClangImplementation(
    private val context: Context,
    authenticationToken: String,
    private val integrationId: String
) : Clang() {

    init {
        ClangApiClient.init(authenticationToken)
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
        NotificationInteractor().logEvent(
            integrationId,
            event,
            data,
            context.getUserId(),
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
        PropertiesInteractor().updateProperties(
            integrationId,
            data,
            context.getUserId(),
            successCallback,
            errorCallback
        )
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

    /** Logs a Notification action
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
        NotificationInteractor().logNotificationAction(
            notificationId,
            context.getUserId(),
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
        TokenInteractor().sendTokenToServer(
            firebaseToken,
            context.getUserId(),
            successCallback,
            errorCallback
        )
    }

    /**
     * Queries FCM token using the [FirebaseInstanceId] class
     *
     * @param onTokenReceived Notifies caller that action was successful returning a FCM token
     */
    //TODO add error case handling
    private fun retrieveFirebaseToken(
        onTokenReceived: (String) -> Unit,
        onTokenFailed: (Throwable) -> Unit
    ) {
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener { onTokenReceived(it.token) }
            .addOnFailureListener { exception -> onTokenFailed(exception)}

    }
}