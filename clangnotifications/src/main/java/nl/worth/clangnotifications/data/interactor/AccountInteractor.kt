package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.BuildConfig
import nl.worth.clangnotifications.data.model.CreateAccountModel
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.data.repository.RemoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException

internal class AccountInteractor {

    fun registerAccount(
        firebaseToken: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {

        val account = CreateAccountModel(firebaseToken, "context.getAndroidId()", BuildConfig.CUSTOMER_ID)
        RemoteRepository.create().createAccount(account).enqueue(object :
            Callback<CreateAccountResponse> {
            override fun onFailure(call: Call<CreateAccountResponse>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<CreateAccountResponse>, response: Response<CreateAccountResponse>) {
                // context.saveIdToSharedPreferences(it.id, it.secret)
                response.body()?.let {
                    successCallback(it)
                } ?: errorCallback(NullPointerException("response null"))
            }
        })
    }
}