package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.TokenUpdateRequest
import nl.worth.clangnotifications.data.repository.RemoteRepository
import nl.worth.clangnotifications.util.authenticationHeader
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class TokenInteractor {
    fun sendTokenToServer(
        firebaseToken: String,
        userId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val account = TokenUpdateRequest(userId, firebaseToken)

        RemoteRepository.create().storeFirebaseToken(
            authenticationHeader("authenticationHeader"), // not required since no Clang API call
            account
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                successCallback()
            }
        })
    }

}