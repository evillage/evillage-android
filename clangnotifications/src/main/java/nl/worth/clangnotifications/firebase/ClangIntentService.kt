package nl.worth.clangnotifications.firebase

import android.app.IntentService
import android.content.Intent

class ClangIntentService : IntentService("ClangIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val id = intent?.getStringExtra("actionId")
        val promotionId = intent?.getStringExtra("productId")
        //TODO send to back end (data what user picked)
    }
}
