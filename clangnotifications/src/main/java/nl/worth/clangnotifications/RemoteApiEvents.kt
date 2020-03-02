package nl.worth.clangnotifications

import android.content.Context
import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.data.interactor.NotificationInteractor
import nl.worth.clangnotifications.data.interactor.PropertiesInteractor
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.util.retrieveFirebaseToken
import nl.worth.clangnotifications.util.retrieveIdFromSP
import nl.worth.clangnotifications.util.retrieveSecretFromSP

internal class RemoteApiEvents(val context: Context) : Clang {
    override fun logEvent(
        context: Context,
        event: String,
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val userId = context.retrieveIdFromSP()
        val secret = context.retrieveSecretFromSP()

        NotificationInteractor().logEvent(event, data, userId, secret, successCallback, errorCallback)
    }

    override fun updateProperties(
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        PropertiesInteractor().updateProperties(data, "id", "secret", successCallback, errorCallback);
    }

    override fun createAccount(
        context: Context,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveFirebaseToken { token ->
            AccountInteractor().registerAccount(
                token,
                context,
                successCallback,
                errorCallback
            )
        }
    }
}