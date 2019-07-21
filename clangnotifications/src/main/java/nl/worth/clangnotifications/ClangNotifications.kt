package nl.worth.clangnotifications

import android.app.Activity
import android.content.Context
import nl.worth.clangnotifications.data.model.CreateAccountResponse

interface ClangNotifications {

    fun createAccountWithUI(activity: Activity): Int

    fun createAccoun(
        email: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    )


    companion object {
        fun getInstance(context: Context): ClangNotifications = ClangNotificationsImpl(context)
    }
}