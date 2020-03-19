package nl.worth.clangnotifications
import android.content.Context
import nl.worth.clangnotifications.data.model.CreateAccountResponse

interface Clang {

    fun createAccount(
        deviceId: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    fun logEvent(
        event: String,
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    fun updateProperties(
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    fun logNotificationAction(
        actionId: String,
        notificationId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    fun updateToken (
        firebaseToken: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    companion object {

        fun getInstance(
            context: Context,
            authenticationToken: String,
            integrationId: String
        ): Clang = RemoteApiEvents(context, authenticationToken, integrationId)
    }
}