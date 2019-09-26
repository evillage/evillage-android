package nl.worth.clangnotifications

import android.content.Context
import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.data.interactor.NotificationInteractor
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.data.model.PollData
import nl.worth.clangnotifications.util.retrieveFirebaseToken
import nl.worth.clangnotifications.util.retrieveIdFromSP

internal class ClangNotificationsImpl(val context: Context) : ClangNotifications {
    override fun logEvent(
        context: Context,
        event: String,
        data: List<PollData>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val userId = context.retrieveIdFromSP()
        NotificationInteractor().logEvent(event, data, userId, successCallback, errorCallback)

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