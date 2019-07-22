package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.BuildConfig
import nl.worth.clangnotifications.data.model.AccountModel
import nl.worth.clangnotifications.data.model.EmptyBody
import nl.worth.clangnotifications.data.repository.RemoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException

internal class TokenInteractor {

    fun sendTokenToServer(
        firebaseToken: String,
        email: String,
        successCallback: (EmptyBody) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val applicationName = BuildConfig.APP_ID
        val account = AccountModel(email, applicationName, firebaseToken)
        RemoteRepository.create().storeFirebaseToken(account).enqueue(object :
            Callback<EmptyBody> {
            override fun onFailure(call: Call<EmptyBody>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<EmptyBody>, response: Response<EmptyBody>) {
                response.body()?.let {
                    successCallback(it)
                } ?: errorCallback(NullPointerException("response null"))
            }
        })
    }

}