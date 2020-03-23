package nl.worth.clangnotifications.firebase

import android.app.IntentService
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import nl.worth.clangnotifications.data.interactor.NotificationInteractor
import nl.worth.clangnotifications.util.getUserId

internal class ClangIntentService : IntentService("ClangIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        val id = intent?.getStringExtra("actionId")
        val notificationId = intent?.getStringExtra("notificationId")

        if (notificationId != null && id != null) {
            NotificationInteractor().logNotificationAction("authHeader", notificationId, applicationContext.getUserId(), id,
                {
                    Toast.makeText(applicationContext, "Thank you for your submit!", Toast.LENGTH_LONG).show()
                },
                {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
            )
        }

        val systemNotificationId = intent?.getIntExtra("systemNotificationId", 0) ?: 0
        NotificationManagerCompat.from(this).cancel(systemNotificationId)
    }
}
