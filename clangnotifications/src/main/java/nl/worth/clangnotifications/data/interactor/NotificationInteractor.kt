package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.EmptyBody
import nl.worth.clangnotifications.data.model.NotoficationTopicRequest
import nl.worth.clangnotifications.data.repository.RemoteRepository
import nl.worth.clangnotifications.util.retrieveFirebaseToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NotificationInteractor {

    fun subscribeToTopic(
        topic: String,
        successCallback: (EmptyBody) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveFirebaseToken { token ->
            val tokens = listOf(token)
            RemoteRepository.create().subscribeToTopic(NotoficationTopicRequest(topic, tokens)).enqueue(object :
                Callback<EmptyBody> {
                override fun onFailure(call: Call<EmptyBody>, t: Throwable) {
                    errorCallback(t)
                }

                override fun onResponse(call: Call<EmptyBody>, response: Response<EmptyBody>) {
                    response.body()?.let {
                        successCallback(it)
                    } ?: errorCallback(NullPointerException("response null"))
                }
            })
        }
    }

    fun unsubscribeFromTopic(
        topic: String,
        successCallback: (EmptyBody) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveFirebaseToken { token ->
            val tokens = listOf(token)
            RemoteRepository.create().unsubscribeFromTopic(NotoficationTopicRequest(topic, tokens)).enqueue(object :
                Callback<EmptyBody> {
                override fun onFailure(call: Call<EmptyBody>, t: Throwable) {
                    errorCallback(t)
                }

                override fun onResponse(call: Call<EmptyBody>, response: Response<EmptyBody>) {
                    response.body()?.let {
                        successCallback(it)
                    } ?: errorCallback(NullPointerException("response null"))
                }
            })
        }
    }
}