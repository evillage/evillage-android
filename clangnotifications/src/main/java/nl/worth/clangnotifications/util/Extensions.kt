package nl.worth.clangnotifications.util

fun String.isEmailValid(): Boolean {
    return isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}