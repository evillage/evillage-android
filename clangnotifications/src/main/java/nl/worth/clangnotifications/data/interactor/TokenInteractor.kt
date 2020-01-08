package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.AccountModel
import nl.worth.clangnotifications.data.repository.RemoteRepository
import nl.worth.clangnotifications.util.authenticationHeader
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class TokenInteractor {

    fun sendTokenToServer(
        firebaseToken: String,
        id: String,
        secret: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val tokens: Array<String> = arrayOf(firebaseToken)
        val account = AccountModel(id, tokens)
        RemoteRepository.create().storeFirebaseToken(authenticationHeader(secret), account).enqueue(object :
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                successCallback()
            }
        })
    }

}