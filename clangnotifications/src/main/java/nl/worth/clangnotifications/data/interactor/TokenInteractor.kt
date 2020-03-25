package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.ClangTokenUpdate
import nl.worth.clangnotifications.data.repository.ClangApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * INSERT CLASS DESCRIPTION HERE
 */
internal class TokenInteractor {

    /**
     * METHOD DESCRIPTION GOES HERE
     *
     * @param firebaseToken FCM token generated for this device
     * @param userId Unique user identifier
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    fun sendTokenToServer(
        firebaseToken: String,
        userId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val account = ClangTokenUpdate(userId, firebaseToken)

        ClangApiClient.getInstance().storeFirebaseToken(
            account
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    successCallback()
                } else {
                    errorCallback(Throwable("Error code not in 200..299"))
                }
            }
        })
    }

}