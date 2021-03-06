package nl.worth.clangnotifications.data.interactor

import androidx.annotation.Keep
import nl.worth.clangnotifications.data.model.ClangActionRequest
import nl.worth.clangnotifications.data.model.ClangEvent
import nl.worth.clangnotifications.data.network.ClangApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository like class that logs Firebase related events
 */
@Keep
class NotificationInteractor {

    /** Logs a Notification action
     *
     * @param notificationId PARAM DESCRIPTION GOES HERE
     * @param userId Unique user identifier
     * @param actionId PARAM DESCRIPTION GOES HERE
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    fun logNotificationAction(
        notificationId: String,
        userId: String,
        actionId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val actionRequest = ClangActionRequest(notificationId, userId, actionId)
        ClangApiClient.getService().logNotificationAction(
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

    /** Logs an event to remote server that may contain additional information
     * For example: event = "login" and data = "mapOf("action" to "login","email" to et_email.text.toString())"
     *
     * @param integrationId PARAM DESCRIPTION GOES HERE
     * @param event The event to log
     * @param data The data of that event
     * @param userId PARAM DESCRIPTION GOES HERE
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
    fun logEvent(
        integrationId: String,
        event: String,
        data: Map<String, String>,
        userId: String,
        successCallback: () -> Unit,
        errorCallback: (Throwable) -> Unit
    ) {
        val eventLogRequest = ClangEvent(userId, event, data, integrationId)

        ClangApiClient.getService().logEvent(eventLogRequest).enqueue(object :
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorCallback(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    successCallback()
                } else {
                    errorCallback(Throwable("Response code not in 200..299, was " + response.code()))
                }
            }
        })
    }
}