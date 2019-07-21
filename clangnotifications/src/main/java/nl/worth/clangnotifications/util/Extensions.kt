package nl.worth.clangnotifications.util

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import nl.worth.clangnotifications.R

internal fun String.isEmailValid(): Boolean {
    return isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

internal fun retrieveFirebaseToken(onTokenReceived: (String) -> Unit) {
    FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) return@OnCompleteListener

            task.result?.let {
                onTokenReceived(it.token)
            }
        })
}

internal fun Context.retrieveEmailFromSP(): String {
    val sharedPref = getSharedPreferences("Clang", Context.MODE_PRIVATE)
    val defaultValue = ""
    val email = sharedPref.getString(getString(R.string.saved_email_key), defaultValue)
    return email ?: defaultValue
}

internal fun Context.saveEmailToSharedPreferences(email: String) {
    val sharedPref = getSharedPreferences("Clang", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString(getString(R.string.saved_email_key), email)
        apply()
    }
}