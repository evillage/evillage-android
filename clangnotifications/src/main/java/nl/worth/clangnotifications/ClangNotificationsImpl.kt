package nl.worth.clangnotifications

import android.app.Activity
import android.content.Context
import android.content.Intent
import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.ui.CreateAccountActivity
import nl.worth.clangnotifications.util.isEmailValid
import nl.worth.clangnotifications.util.retrieveFirebaseToken
import nl.worth.clangnotifications.util.saveEmailToSharedPreferences

internal class ClangNotificationsImpl(val context: Context) : ClangNotifications {

    override fun createAccount(
        email: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        if (email.isEmailValid()) context.saveEmailToSharedPreferences(email)
        retrieveFirebaseToken { token ->
            AccountInteractor().registerAccount(
                email,
                token,
                successCallback,
                errorCallback
            )
        }
    }

    override fun createAccountWithUI(activity: Activity): Int {
        val requestCode = 200
        activity.startActivityForResult(Intent(activity, CreateAccountActivity::class.java), requestCode)
        return requestCode
    }
}