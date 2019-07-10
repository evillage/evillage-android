package nl.worth.clangnotifications

import android.app.Activity
import android.content.Intent
import nl.worth.clangnotifications.ui.CreateAccountActivity

internal class ClangNotificationsImpl: ClangNotifications {

    override fun createAccountWithUI(activity: Activity) : Int {
        val requestCode = 200
        activity.startActivityForResult(Intent(activity, CreateAccountActivity::class.java), requestCode)
        return requestCode
    }
}