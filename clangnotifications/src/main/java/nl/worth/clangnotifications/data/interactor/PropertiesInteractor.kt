package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.ClangProperties
import nl.worth.clangnotifications.data.repository.ClangApiClient
import nl.worth.clangnotifications.util.authenticationHeader
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * INSERT CLASS DESCRIPTION HERE
 */
internal class PropertiesInteractor {

    /**
     * METHOD DESCRIPTION GOES HERE
     *
     * @param authenticationToken PARAM DESCRIPTION GOES HERE
     * @param integrationId PARAM DESCRIPTION GOES HERE
     * @param data PARAM DESCRIPTION GOES HERE
     * @param userId PARAM DESCRIPTION GOES HERE
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    fun updateProperties (
        authenticationToken: String,
        integrationId: String,
        data: Map<String, String>,
        userId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val propertiesRequest = ClangProperties(userId, integrationId, data)

        ClangApiClient.getInstance().updateProperties(
            authenticationHeader(authenticationToken),
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