package nl.worth.clangnotifications

import android.app.Activity
import android.content.Context
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import nl.worth.clangnotifications.data.model.ClangAccountResponse
import java.lang.NullPointerException

/**
 * Abstract access class for Clang logging services.
 *
 * Call setUp() at least once in your Application class to initialize the library.
 * Call getInstance() after setUp() to get a reference to the Singleton object.
 *
 * It is advised to keep a reference of the Singleton Clang object to you Application class and use that when logging instead of calling getInstance() every time you need one.
 */
@Keep
abstract class Clang {

    abstract fun createAccount(
        deviceId: String,
        successCallback: (ClangAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    /// Log an event to the Clang backend
    /// - Parameters:
    ///   - data: The data you want to log for this event
    ///   - token: The token we receive from Firebase when registering for push notifications
    ///   - login: Event date of the event you want to log
    ///   - data:  Proporties you want to log
    fun registerAccountWithProperties(token : String, data  : Map<String, String>, login  : Map<String, String>) {

        updateToken(token,{ }, {})
        updateProperties(data, {}, {})
        logEvent("login", login, {}, {})

    }

    fun logEventWithToken(mail: String, token : String) {

        logEvent("login", mapOf("title" to "login","userEmail" to mail), {

        }, {

        })

        updateToken(token,{

        }, {

        })

    }

    abstract fun logEvent(
        event: String,
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    abstract fun updateToken(
        firebaseToken: String,
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


    @Keep
    companion object {
        @Volatile
        private var instance: ClangImplementation? = null
        private var authenticationToken: String = ""
        private var integrationId: String = ""

        /**
         * Call once in your Application class to create Singleton object
         */
        fun setUp(
            context: Context,
            baseUrl: String,
            authenticationToken: String,
            integrationId: String
        ) {
            if (instance == null) {
                instance = ClangImplementation(baseUrl, authenticationToken, integrationId)
                instance!!.context = context
            }
        }


        fun setAuthenticationToken(authenticationToken: String) {
            this.authenticationToken = authenticationToken
        }

        fun setIntegrationId(integrationId: String) {
            this.integrationId = integrationId
        }

        /**
         * Call once in your Application class to create Singleton object
         *
         * - Uses default URL
         */
        fun setUp(
            context: Context,
            authenticationToken: String,
            integrationId: String
        ) {
            if (instance == null) {
                instance = ClangImplementation(authenticationToken, integrationId)
                instance!!.context = context
            }
        }

        /**
         * Call to get instance of Clang
         *
         * It's advised to keep a reference to a Clang object in your Application class instead of calling getInstance() every time you need one
         */
        fun getInstance(): Clang {
            return if (instance == null) {
                throw NullPointerException("Call setUp() first at least once before calling getInstance()")
            } else {
                instance as ClangImplementation
            }
        }
    }
}