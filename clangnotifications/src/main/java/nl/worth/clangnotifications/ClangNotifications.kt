package nl.worth.clangnotifications

import android.content.Context
import nl.worth.clangnotifications.data.model.CreateAccountResponse

interface ClangNotifications {

    fun createAccount(
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    )


    companion object {
        fun getInstance(context: Context): ClangNotifications = ClangNotificationsImpl(context)
    }
}