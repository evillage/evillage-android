package nl.worth.clangnotifications.util

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

fun String.isEmailValid(): Boolean {
    return isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun retrieveToken(onTokenReceived: (String) -> Unit) {
    FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) return@OnCompleteListener

            task.result?.let {
                onTokenReceived(it.token)
            }
        })
}