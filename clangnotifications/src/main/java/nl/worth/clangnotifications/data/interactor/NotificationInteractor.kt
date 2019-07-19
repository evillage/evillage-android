package nl.worth.clangnotifications.data.interactor

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import nl.worth.clangnotifications.data.model.NotoficationTopicRequest
import nl.worth.clangnotifications.data.repository.RemoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationInteractor {

    fun subscribeToTopic(
        topic: String,
        successCallback: (Unit) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveCurrentFirebaseToken { token ->
            val tokens = mutableListOf(token)
            RemoteRepository.create().subscribeToTopic(NotoficationTopicRequest(topic, tokens)).enqueue(object :
                Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    errorCallback(t)
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    response.body()?.let {
                        successCallback(it)
                    } ?: errorCallback(NullPointerException("response null"))
                }
            })
        }

    }

    fun unsubscribeFromTopic(
        topic: String,
        successCallback: (Unit) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        retrieveCurrentFirebaseToken { token ->
            val tokens = mutableListOf(token)
            RemoteRepository.create().unsubscribeFromTopic(NotoficationTopicRequest(topic, tokens)).enqueue(object :
                Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    errorCallback(t)
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    response.body()?.let {
                        successCallback(it)
                    } ?: errorCallback(NullPointerException("response null"))
                }
            })
        }
    }

    private fun retrieveCurrentFirebaseToken(onTokenReceived: (String) -> Unit) {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                task.result?.let {
                    onTokenReceived(it.token)
                }
            })
    }

}