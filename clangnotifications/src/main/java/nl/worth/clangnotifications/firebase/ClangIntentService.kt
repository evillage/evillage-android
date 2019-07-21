package nl.worth.clangnotifications.firebase

import android.app.IntentService
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

internal class ClangIntentService : IntentService("ClangIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val id = intent?.getStringExtra("actionId")
        val promotionId = intent?.getStringExtra("productId")
        val notificationId = intent?.getStringExtra("notificationId")?.toInt() ?: 0
        NotificationManagerCompat.from(this).cancel(notificationId)
        //TODO send to back end (data what user picked)
    }
}
