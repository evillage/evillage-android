package nl.worth.clangnotifications.util

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Keep
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.KeyStore


/**
 * Saves the registered user id into [EncryptedSharedPreferences]
 *
 * @param userId The id of the current user's registered account
 */
@Keep
fun Context.saveUserId(userId: String) {
    makeSharedPreferences(this).edit().putString(userIdKey, userId).apply()
}

/**
 * Fetches the user id from [EncryptedSharedPreferences]
 */
@Keep
fun Context.getUserId(): String? {
    return getSharedPreferences(this).getString(userIdKey, null)
}

/**
 * Creates and returns an EncryptedSharedPreferences instance
 *
 * @return SharedPreferences instance
 */
@Keep
fun makeSharedPreferences(context: Context): SharedPreferences {

    val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
    keyStore.load( /* param= */null)
    keyStore.deleteEntry(MasterKey.DEFAULT_MASTER_KEY_ALIAS);

    context.getSharedPreferences(clangFile, Context.MODE_PRIVATE).edit().clear().apply()


    val mainKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()


    if (mainKey.isKeyStoreBacked) {


        val masterKey = MasterKey
            .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .setUserAuthenticationRequired(false)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            clangFile,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    } else {

        val masterKey = MasterKey
            .Builder(context, "ClangCode")
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .setUserAuthenticationRequired(false)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            clangFile,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    }

}

fun getSharedPreferences(context: Context): SharedPreferences {

    val masterKey = MasterKey
        .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .setUserAuthenticationRequired(false)
        .build()




    return EncryptedSharedPreferences.create(
        context,
        clangFile,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}





