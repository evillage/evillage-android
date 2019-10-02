package nl.worth.clangnotifications.util

import android.content.Context
import android.provider.Settings
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import nl.worth.clangnotifications.R

internal fun retrieveFirebaseToken(onTokenReceived: (String) -> Unit) {
    FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) return@OnCompleteListener

            task.result?.let {
                onTokenReceived(it.token)
            }
        })
}

internal fun Context.retrieveIdFromSP(): String {
    val sharedPref = getSharedPreferences("Clang", Context.MODE_PRIVATE)
    val defaultValue = ""
    val userId = sharedPref.getString(getString(R.string.saved_id_key), defaultValue)
    return userId ?: defaultValue
}

internal fun Context.saveIdToSharedPreferences(id: String) {
    val sharedPref = getSharedPreferences("Clang", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString( getString(R.string.saved_id_key), id)
        apply()
    }
}

internal fun Context.getAndroidId(): String {
    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
}