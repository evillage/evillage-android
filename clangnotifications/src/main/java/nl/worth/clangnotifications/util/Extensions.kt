package nl.worth.clangnotifications.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.iid.FirebaseInstanceId

/**
 * Queries FCM token using the [FirebaseInstanceId] class
 *
 * @param onTokenReceived Notifies caller that action was successful returning a FCM token
 */
//TODO add error case handling
internal fun retrieveFirebaseToken(onTokenReceived: (String) -> Unit) {
    FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) return@OnCompleteListener

            task.result?.let {
                onTokenReceived(it.token)
            }
        })
        .addOnFailureListener(OnFailureListener { task ->
            //TODO
        })
}

/**
 * Saves the registered user id into [EncryptedSharedPreferences]
 *
 * @param userId The id of the current user's registered account
 */
internal fun Context.saveUserId(userId: String) {
    val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "clang",
        masterKeyAlias,
        this,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    sharedPreferences.edit().apply {
        putString("user.id", userId)
        apply()
    }
}

/**
 * Fetches the user id from [EncryptedSharedPreferences]
 */
internal fun Context.getUserId(): String {
    val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "clang",
        masterKeyAlias,
        this,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    return sharedPreferences.getString("user.id", "").orEmpty()
}

/**
 * Adds String "Bearer" in front of the authorization header
 */
internal fun authenticationHeader(bearerToken: String): String {
    if (bearerToken.isNotEmpty()) {
        return "Bearer $bearerToken"
    }
    return ""
}
