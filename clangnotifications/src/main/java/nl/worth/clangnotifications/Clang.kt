package nl.worth.clangnotifications

import android.content.Context
import nl.worth.clangnotifications.data.model.ClangAccountResponse
import java.lang.NullPointerException

/**
 * Abstract access class for Clang logging services.
 *
 * Call setUp() at least once in your Application class to initialize the library.
 * Call getInstance() after setUp() to get a reference to the Singleton object.
 *
 * It is advised to keep a reference of the Singleton to you Application class and use that when logging instead of calling getInstance() every time.
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

        fun setUp(context: Context,
                   authenticationToken: String,
                   integrationId: String){
            if (instance == null) {
                instance = ClangImplementation(authenticationToken, integrationId)
            }
        }

        fun getInstance(context: Context): Clang {
            return if (instance == null) {
                throw NullPointerException("Call setUp() first at least once before calling getInstance()")
            } else {
                instance!!.context = context
                instance as ClangImplementation
            }
        }
    }
}