package nl.worth.clangnotifications

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
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
    private val integrationId: String) : Clang() {

    init {
        ClangApiClient.init(authenticationToken)
    }

    /** Sends an event to remote server
    *
    * @param event The event to log
    * @param data PARAM DESCRIPTION GOES HERE
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

    /** METHOD DESCRIPTION GOES HERE
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
        retrieveFirebaseToken { token ->
            AccountInteractor().registerAccount(
                context,
                integrationId,
                token,
                deviceId,
                successCallback,
                errorCallback
            )
        }
    }

    /** METHOD DESCRIPTION GOES HERE
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

    /** METHOD DESCRIPTION GOES HERE
     *
     * @param firebaseToken FCM token generated for this device
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    override fun updateToken (
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
    private fun retrieveFirebaseToken(onTokenReceived: (String) -> Unit) {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) return@OnCompleteListener
                task.result?.let {
                    onTokenReceived(it.token)
                }
            })
    }
}