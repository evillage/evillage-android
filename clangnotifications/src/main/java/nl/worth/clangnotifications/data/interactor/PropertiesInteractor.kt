package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.ClangProperties
import nl.worth.clangnotifications.data.network.ClangApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository like class that ..
 */
internal class PropertiesInteractor {

    /**
     * METHOD DESCRIPTION GOES HERE
     *
     * @param integrationId PARAM DESCRIPTION GOES HERE
     * @param data PARAM DESCRIPTION GOES HERE
     * @param userId Unique user identifier
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    fun updateProperties (
        integrationId: String,
        data: Map<String, String>,
        userId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val propertiesRequest = ClangProperties(userId, integrationId, data)

        ClangApiClient.getService().updateProperties(
            propertiesRequest
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    successCallback()
                }else{
                    errorCallback(Throwable("Error code not in 200..299"))
                }
            }
        })
    }
}