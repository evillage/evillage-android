package nl.worth.clangnotifications

import android.content.Context
import nl.worth.clangnotifications.data.model.ClangAccountResponse

/**
 *
 */
abstract class Clang {

    abstract fun createAccount(
        deviceId: String,
        successCallback: (ClangAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    abstract fun logEvent(
        event: String,
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    abstract fun updateProperties(
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    abstract fun logNotificationAction(
        actionId: String,
        notificationId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    abstract fun updateToken(
        firebaseToken: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    /**
     * Returns the same instance of an [ClangImplementation] class if previously created, else it returns a new instance casted as [Clang]
     */
    companion object {
        @Volatile
        private var instance: ClangImplementation? = null

        fun getInstance(
            context: Context,
            authenticationToken: String,
            integrationId: String
        ): Clang {
            return if (instance == null) {
                ClangImplementation(context, authenticationToken, integrationId)
            } else {
                instance as ClangImplementation
            }
        }
    }
}