package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.CreateAccountModel
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import nl.worth.clangnotifications.data.repository.RemoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException
import nl.worth.clangnotifications.util.saveIdToSharedPreferences

internal class AccountInteractor {

    fun registerAccount(
        firebaseToken: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val account = CreateAccountModel(firebaseToken)
        RemoteRepository.create().createAccount(account).enqueue(object :
            Callback<CreateAccountResponse> {
            override fun onFailure(call: Call<CreateAccountResponse>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<CreateAccountResponse>, response: Response<CreateAccountResponse>) {
                response.body()?.let {
                    saveIdToSharedPreferences(it.id)
                    successCallback(it)
                } ?: errorCallback(NullPointerException("response null"))
            }
        })
    }
}