package nl.worth.clangnotifications.data.repository

import nl.worth.clangnotifications.BuildConfig
import nl.worth.clangnotifications.data.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface RemoteRepository {

    @POST("api/v1/account/register")
    fun createAccount(@Body createAccountModel: CreateAccountModel): Call<CreateAccountResponse>

    @POST("api/v1/token/save")
    fun storeFirebaseToken(@Header("authorization") secretHeader: String,
                           @Body tokenRequest: AccountModel): Call<ResponseBody>

    @POST("/api/v1/notification/action")
    fun logNotificationAction(@Header("authorization") secretHeader: String,
                              @Body actionRequest: ActionRequest): Call<ResponseBody>

    @POST("/api/v1/notification/event")
    fun logEvent(@Header("authorization") secretHeader: String,
                 @Body eventLog: EventLogRequest): Call<ResponseBody>

    @POST("/api/v1/properties")
    fun updateProperties(@Header("authorization") secretHeader: String,
                         @Body properties: PropertiesRequest): Call<ResponseBody>

    companion object {
        fun create(): RemoteRepository {

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.REMOTE_REPO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RemoteRepository::class.java)
        }
    }
}