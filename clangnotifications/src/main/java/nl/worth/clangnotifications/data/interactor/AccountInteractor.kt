package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.BuildConfig
import nl.worth.clangnotifications.data.model.AccountModel
import nl.worth.clangnotifications.util.isEmailValid
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.data.repository.RemoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException

class AccountInteractor {

    fun registerAccount(
        email: String,
        firebaseToken: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        if (email.isEmailValid()) {
            val applicationName = BuildConfig.APPLICATION_ID
            val account = AccountModel(email, applicationName, firebaseToken)
            RemoteRepository.create().createAccount(account).enqueue(object :
                Callback<CreateAccountResponse> {
                override fun onFailure(call: Call<CreateAccountResponse>, t: Throwable) {
                    errorCallback(t)
                }

                override fun onResponse(call: Call<CreateAccountResponse>, response: Response<CreateAccountResponse>) {
                    response.body()?.let {
                        successCallback(it)
                    } ?: errorCallback(NullPointerException("response null"))
                }
            })
        } else errorCallback(NullPointerException("email is not valid"))
    }
}