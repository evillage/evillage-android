package nl.worth.clangnotifications

import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.data.interactor.NotificationInteractor
import nl.worth.clangnotifications.data.interactor.PropertiesInteractor
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.util.retrieveFirebaseToken

internal class RemoteApiEvents() : Clang {
    override fun logEvent(
        event: String,
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        NotificationInteractor().logEvent(event, data, "userId", "secret", successCallback, errorCallback)
    }

    override fun updateProperties(
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        PropertiesInteractor().updateProperties(data, "id", "secret", successCallback, errorCallback);
    }

    override fun createAccount(
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveFirebaseToken { token ->
            AccountInteractor().registerAccount(
                token,
                successCallback,
                errorCallback
            )
        }
    }
}