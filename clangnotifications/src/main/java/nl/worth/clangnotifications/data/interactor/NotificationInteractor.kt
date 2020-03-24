package nl.worth.clangnotifications.data.interactor

import nl.worth.clangnotifications.data.model.ClangActionRequest
import nl.worth.clangnotifications.data.model.ClangEvent
import nl.worth.clangnotifications.data.repository.ClangApiClient
import nl.worth.clangnotifications.util.authenticationHeader
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * INSERT CLASS DESCRIPTION HERE
 */
internal class NotificationInteractor {

    /**
     * METHOD DESCRIPTION GOES HERE
     *
     * @param authenticationToken PARAM DESCRIPTION GOES HERE
     * @param notificationId PARAM DESCRIPTION GOES HERE
     * @param userId PARAM DESCRIPTION GOES HERE
     * @param actionId PARAM DESCRIPTION GOES HERE
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
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

    /**
     * METHOD DESCRIPTION GOES HERE
     *
     * @param authenticationToken PARAM DESCRIPTION GOES HERE
     * @param integrationId PARAM DESCRIPTION GOES HERE
     * @param event PARAM DESCRIPTION GOES HERE
     * @param data PARAM DESCRIPTION GOES HERE
     * @param userId PARAM DESCRIPTION GOES HERE
     * @param successCallback Notifies caller that action was successful
     * @param errorCallback Notifies caller that action failed returning a Throwable
     */
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
                if(response.isSuccessful){
                    successCallback()
                }else{
                    errorCallback(Throwable("Error: code not in 200..299"))
                }            }
        })
    }
}