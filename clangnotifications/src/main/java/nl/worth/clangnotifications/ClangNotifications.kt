package nl.worth.clangnotifications

import android.content.Context
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.data.model.PollData

interface ClangNotifications {

    fun createAccount(
        context: Context,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    fun logEvent(
        context: Context,
        event: String,
        data: List<PollData>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    companion object {
        fun getInstance(context: Context): ClangNotifications = ClangNotificationsImpl(context)
    }
}