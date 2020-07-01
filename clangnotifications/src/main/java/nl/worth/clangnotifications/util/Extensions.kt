package nl.worth.clangnotifications.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


/**
 * Saves the registered user id into [EncryptedSharedPreferences]
 *
 * @param userId The id of the current user's registered account
 */
internal fun Context.saveUserId(userId: String) {
    getSharedPreferences(this).edit().putString(userIdKey, userId).apply()
}

/**
 * Fetches the user id from [EncryptedSharedPreferences]
 */
internal fun Context.getUserId(): String? {
    return getSharedPreferences(this).getString(userIdKey, null)
}

/**
 * Creates and returns an EncryptedSharedPreferences instance
 *
 * @return SharedPreferences instance
 */
fun getSharedPreferences(context: Context): SharedPreferences {
    val masterKey = MasterKey
        .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    return EncryptedSharedPreferences.create(
        context,
        clangFile,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

