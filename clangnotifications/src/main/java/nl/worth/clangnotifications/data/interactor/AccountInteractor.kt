package nl.worth.clangnotifications.data.interactor

import android.content.Context
import nl.worth.clangnotifications.data.model.ClangAccount
import nl.worth.clangnotifications.data.model.ClangAccountResponse
import nl.worth.clangnotifications.data.repository.ClangApiClient
import nl.worth.clangnotifications.util.authenticationHeader
import nl.worth.clangnotifications.util.saveUserId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 */
internal class AccountInteractor {

    fun registerAccount(
        context: Context,
        authenticationToken: String,
        integrationId: String,
        firebaseToken: String,
        deviceId: String,
        successCallback: (ClangAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val account = ClangAccount(firebaseToken, deviceId, integrationId)
        ClangApiClient.getInstance().createAccount(authenticationHeader(authenticationToken), account).enqueue(object :
            Callback<ClangAccountResponse> {
            override fun onFailure(call: Call<ClangAccountResponse>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<ClangAccountResponse>, response: Response<ClangAccountResponse>) {
                response.body()?.let {
                    context.saveUserId(it.id)
                    successCallback(it)
                } ?: errorCallback(NullPointerException("Null response"))
            }
        })
    }
}