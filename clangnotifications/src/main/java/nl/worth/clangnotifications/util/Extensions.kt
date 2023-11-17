package nl.worth.clangnotifications.util

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Keep


@Keep
fun Context.saveUserId(userId: String) {
    val sharedPreferences: SharedPreferences =
        getSharedPreferences("clang_preferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("userId", userId)
    editor.apply()
}

@Keep
fun Context.getUserId(): String? {
    val sharedPreferences: SharedPreferences =
        getSharedPreferences("clang_preferences", Context.MODE_PRIVATE)
    return sharedPreferences.getString("userId", null)
}



