package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.NotoficationTopicRequest
import nl.worth.clangnotifications.data.repository.RemoteRepository
import nl.worth.clangnotifications.util.retrieveFirebaseToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NotificationInteractor {

    fun subscribeToTopic(
        topic: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveFirebaseToken { token ->
            val tokens = listOf(token)
            RemoteRepository.create().subscribeToTopic(NotoficationTopicRequest(topic, tokens)).enqueue(object :
                Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    errorCallback(t)
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    successCallback()
                }
            })
        }
    }

    fun unsubscribeFromTopic(
        topic: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveFirebaseToken { token ->
            val tokens = listOf(token)
            RemoteRepository.create().unsubscribeFromTopic(NotoficationTopicRequest(topic, tokens)).enqueue(object :
                Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    errorCallback(t)
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    successCallback()
                }
            })
        }
    }
}