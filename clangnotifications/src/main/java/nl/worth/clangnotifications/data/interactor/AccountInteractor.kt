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
 * INSERT CLASS DESCRIPTION HERE
 */
internal class AccountInteractor {

    /**
     * METHOD DESCRIPTION GOES HERE
     *
     * @param context Used to get access to EncryptedSharedPreferences
     * @param authenticationToken PARAM DESCRIPTION GOES HERE
     * @param integrationId PARAM DESCRIPTION GOES HERE
     * @param firebaseToken PARAM DESCRIPTION GOES HERE
     * @param deviceId PARAM DESCRIPTION GOES HERE
     * @param successCallback Notifies caller that action was successful returning an [ClangAccountResponse] object
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
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
        ClangApiClient.getInstance()
            .createAccount(authenticationHeader(authenticationToken), account).enqueue(object :
                Callback<ClangAccountResponse> {
                override fun onFailure(call: Call<ClangAccountResponse>, t: Throwable) {
                    errorCallback(t)
                }

                override fun onResponse(
                    call: Call<ClangAccountResponse>,
                    response: Response<ClangAccountResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            context.saveUserId(it.id)
                            successCallback(it)
                        } ?: errorCallback(Throwable("Response was null"))
                    } else {
                        errorCallback(Throwable("Error: code not in 200..299"))
                    }
                }
            })
    }
}