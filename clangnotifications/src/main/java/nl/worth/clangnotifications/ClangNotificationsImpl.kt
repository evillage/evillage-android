package nl.worth.clangnotifications

import android.content.Context
import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.util.retrieveFirebaseToken

internal class ClangNotificationsImpl(val context: Context) : ClangNotifications {

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