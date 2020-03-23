package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.PropertiesRequest
import nl.worth.clangnotifications.data.repository.RemoteRepository
import nl.worth.clangnotifications.util.authenticationHeader
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class PropertiesInteractor {

    fun updateProperties (
        authenticationToken: String,
        integrationId: String,
        data: Map<String, String>,
        userId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val propertiesRequest = PropertiesRequest(userId, integrationId, data)

        RemoteRepository.create().updateProperties(
            authenticationHeader(authenticationToken),
            propertiesRequest
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                successCallback()
            }
        })
    }
}