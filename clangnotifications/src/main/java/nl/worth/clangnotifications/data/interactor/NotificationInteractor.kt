package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.ActionRequest
import nl.worth.clangnotifications.data.model.EventLogRequest
import nl.worth.clangnotifications.data.model.PollData
import nl.worth.clangnotifications.data.repository.RemoteRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NotificationInteractor {

    fun logNotificationAction(
        notificationId: String,
        userId: String,
        actionId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val actionRequest = ActionRequest(notificationId, userId, actionId)
        RemoteRepository.create().logNotificationAction(actionRequest).enqueue(object :
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
        data: List<PollData>,
        userId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val eventLogRequest = EventLogRequest(userId, event, data)
        RemoteRepository.create().logEvent(eventLogRequest).enqueue(object :
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