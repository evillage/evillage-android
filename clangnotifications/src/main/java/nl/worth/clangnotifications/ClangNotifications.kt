package nl.worth.clangnotifications

import android.app.Activity

interface ClangNotifications {

    fun createAccountWithUI(activity: Activity) : Int

    companion object {
        fun getInstance(): ClangNotifications = ClangNotificationsImpl()
    }
}