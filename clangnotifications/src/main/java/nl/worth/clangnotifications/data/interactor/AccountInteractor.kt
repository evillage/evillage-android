package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.util.isEmailValid
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import java.lang.NullPointerException

class AccountInteractor {

    fun registerAccount(
        email: String,
        successCallback: (CreateAccountResponse) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        if (email.isEmailValid())
            successCallback(CreateAccountResponse("qwerty"))
//            RemoteRepository.create().createAccount(CreateAccountRequest(email)).enqueue(object :
//                Callback<CreateAccountResponse> {
//                override fun onFailure(call: Call<CreateAccountResponse>, t: Throwable) {
//                    errorCallback(t)
//                }
//
//                override fun onResponse(call: Call<CreateAccountResponse>, response: Response<CreateAccountResponse>) {
//                    response.body()?.let {
//                        successCallback(it)
//                    } ?: errorCallback(NullPointerException("response null"))
//                }
//            })
        else
            errorCallback(NullPointerException("email is not valid"))
    }
}