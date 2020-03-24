package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.ClangActionRequest
import nl.worth.clangnotifications.data.model.ClangEvent
import nl.worth.clangnotifications.data.repository.ClangApiClient
import nl.worth.clangnotifications.util.authenticationHeader
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NotificationInteractor {

    fun logNotificationAction(
        authenticationToken: String,
        notificationId: String,
        userId: String,
        actionId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val actionRequest = ClangActionRequest(notificationId, userId, actionId)

        ClangApiClient.getInstance().logNotificationAction(
            authenticationHeader(authenticationToken),
            actionRequest
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                successCallback()
            }
        })
    }

    fun logEvent(
        authenticationToken: String,
        integrationId: String,
        event: String,
        data: Map<String, String>,
        userId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val eventLogRequest = ClangEvent(userId, event, data, integrationId)

        ClangApiClient.getInstance().logEvent(authenticationHeader(authenticationToken), eventLogRequest).enqueue(object :
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