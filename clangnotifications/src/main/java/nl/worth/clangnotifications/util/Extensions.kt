package nl.worth.clangnotifications.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.iid.FirebaseInstanceId

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

