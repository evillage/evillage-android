package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.ActionRequest
import nl.worth.clangnotifications.data.model.EventLogRequest
import nl.worth.clangnotifications.data.model.PollData
import nl.worth.clangnotifications.data.repository.RemoteRepository
import nl.worth.clangnotifications.util.authenticationHeader
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NotificationInteractor {

    fun logNotificationAction(
        notificationId: String,
        userId: String,
        actionId: String,
        secret: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val actionRequest = ActionRequest(notificationId, userId, actionId)
        RemoteRepository.create().logNotificationAction(authenticationHeader(secret), actionRequest).enqueue(object :
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                successCallback()
            }
        })
    }

    fun logEvent(
        event: String,
        data: Map<String, String>,
        userId: String,
        secret: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val eventLogRequest = EventLogRequest(userId, event, data)
        RemoteRepository.create().logEvent(authenticationHeader(secret), eventLogRequest).enqueue(object :
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