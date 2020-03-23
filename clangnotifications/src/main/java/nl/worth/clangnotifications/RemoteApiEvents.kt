package nl.worth.clangnotifications

import android.content.Context
import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.data.interactor.NotificationInteractor
import nl.worth.clangnotifications.data.interactor.PropertiesInteractor
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.util.getUserId
import nl.worth.clangnotifications.util.retrieveFirebaseToken

internal class RemoteApiEvents(
    private val context: Context,
    private val authenticationToken: String,
    private val integrationId: String) : Clang {

    override fun logEvent(
        event: String,
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        NotificationInteractor().logEvent(
            authenticationToken,
            integrationId,
            event,
            data,
            context.getUserId(),
            successCallback,
            errorCallback
        )
    }

    override fun updateProperties(
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        PropertiesInteractor().updateProperties(
            authenticationToken,
            integrationId,
            data,
            context.getUserId(),
            successCallback,
            errorCallback
        )
    }

    override fun createAccount(
        deviceId: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveFirebaseToken { token ->
            AccountInteractor().registerAccount(
                context,
                authenticationToken,
                integrationId,
                token,
                deviceId,
                successCallback,
                errorCallback
            )
        }
    }
}