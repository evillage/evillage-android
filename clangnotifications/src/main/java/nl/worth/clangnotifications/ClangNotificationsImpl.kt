package nl.worth.clangnotifications

import android.app.Activity
import android.content.Context
import android.content.Intent
import nl.worth.clangnotifications.data.interactor.AccountInteractor
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.ui.CreateAccountActivity
import nl.worth.clangnotifications.util.isEmailValid
import nl.worth.clangnotifications.util.retrieveToken

internal class ClangNotificationsImpl(val context: Context) : ClangNotifications {

    override fun createAccoun(
        email: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        if (email.isEmailValid()) saveEmailToSharedPreferences(email)
        retrieveToken { token ->
            AccountInteractor().registerAccount(
                email,
                token,
                successCallback,
                errorCallback
            )
        }
    }

    private fun saveEmailToSharedPreferences(email: String) {
        val sharedPref = context.getSharedPreferences("Clang", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(context.getString(R.string.saved_email_key), email)
            apply()
        }
    }

    override fun createAccountWithUI(activity: Activity): Int {
        val requestCode = 200
        activity.startActivityForResult(Intent(activity, CreateAccountActivity::class.java), requestCode)
        return requestCode
    }
}