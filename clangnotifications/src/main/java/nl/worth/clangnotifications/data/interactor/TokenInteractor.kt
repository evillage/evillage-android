package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.BuildConfig
import nl.worth.clangnotifications.data.model.AccountModel
import nl.worth.clangnotifications.data.repository.RemoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException

internal class TokenInteractor {

    fun sendTokenToServer(
        firebaseToken: String,
        email: String,
        successCallback: (Unit) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val applicationName = BuildConfig.APPLICATION_ID
        val account = AccountModel(email, applicationName, firebaseToken)
        RemoteRepository.create().storeFirebaseToken(account).enqueue(object :
            Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                response.body()?.let {
                    successCallback(it)
                } ?: errorCallback(NullPointerException("response null"))
            }
        })
    }

}