package nl.worth.clangnotifications.data.repository

import nl.worth.clangnotifications.data.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ClangApiService {

    @POST("api/v1/account/register")
    fun createAccount(@Header("authorization") bearerToken: String,
                      @Body clangAccount: ClangAccount): Call<ClangAccountResponse>

    @POST("api/v1/token/save")
    fun storeFirebaseToken(@Header("authorization") bearerToken: String,
                           @Body clangToken: ClangTokenUpdate): Call<ResponseBody>

    @POST("/api/v1/notification/action")
    fun logNotificationAction(@Header("authorization") bearerToken: String,
                              @Body clangActionRequest: ClangActionRequest): Call<ResponseBody>

    @POST("/api/v1/notification/event")
    fun logEvent(@Header("authorization") bearerToken: String,
                 @Body clangEventLog: ClangEvent): Call<ResponseBody>

    @POST("/api/v1/properties")
    fun updateProperties(@Header("authorization") bearerToken: String,
                         @Body clangProperties: ClangProperties): Call<ResponseBody>
}