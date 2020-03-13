package nl.worth.clangnotifications
import nl.worth.clangnotifications.data.model.CreateAccountResponse

interface Clang {

    fun createAccount(
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    fun logEvent(
        event: String,
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    fun updateProperties(
        data: Map<String, String>,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    )

    companion object {
        fun getInstance(): Clang = RemoteApiEvents()
    }
}