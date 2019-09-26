package nl.worth.clangnotifications

import android.content.Context
import nl.worth.clangnotifications.data.model.CreateAccountResponse

interface Clang {

    fun createAccount(
        context: Context,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    fun logEvent(
        context: Context,
        event: String,
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    companion object {
        fun getInstance(context: Context): Clang = RemoteApiEvents(context)
    }
}