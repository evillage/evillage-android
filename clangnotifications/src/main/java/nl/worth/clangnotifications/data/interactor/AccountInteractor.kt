package nl.worth.clangnotifications.data.interactor

import android.content.Context
import nl.worth.clangnotifications.data.model.CreateAccountModel
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.data.repository.RemoteRepository
import nl.worth.clangnotifications.util.authenticationHeader
import nl.worth.clangnotifications.util.saveUserId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException

internal class AccountInteractor {

    fun registerAccount(
        context: Context,
        authenticationToken: String,
        integrationId: String,
        firebaseToken: String,
        deviceId: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val account = CreateAccountModel(firebaseToken, deviceId, integrationId)
        RemoteRepository.create().createAccount(authenticationHeader(authenticationToken), account).enqueue(object :
            Callback<CreateAccountResponse> {
            override fun onFailure(call: Call<CreateAccountResponse>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<CreateAccountResponse>, response: Response<CreateAccountResponse>) {
                response.body()?.let {
                    context.saveUserId(it.id)
                    successCallback(it)

                } ?: errorCallback(NullPointerException("Null response"))
            }
        })
    }
}