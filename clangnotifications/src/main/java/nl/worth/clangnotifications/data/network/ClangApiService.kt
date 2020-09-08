package nl.worth.clangnotifications.data.network

import androidx.annotation.Keep
import nl.worth.clangnotifications.data.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit

/**
 * Interface used to generate code for API calls used by [Retrofit]
 */
@Keep
interface ClangApiService {

    @POST("/push/account/register")
    fun createAccount(@Body clangAccount: ClangAccount): Call<ClangAccountResponse>

    @POST("/push/token/save")
    fun storeFirebaseToken(@Body clangToken: ClangTokenUpdate): Call<ResponseBody>

    @POST("/push/notification/action")
    fun logNotificationAction(@Body clangActionRequest: ClangActionRequest): Call<ResponseBody>

    @POST("/push/notification/event")
    fun logEvent(@Body clangEventLog: ClangEvent): Call<ResponseBody>

    @POST("/push/properties")
    fun updateProperties(@Body clangProperties: ClangProperties): Call<ResponseBody>
}