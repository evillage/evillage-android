package nl.worth.clangnotifications.data.repository

import nl.worth.clangnotifications.BuildConfig
import nl.worth.clangnotifications.data.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

internal interface RemoteRepository {

    @POST("/push/account/register")
    fun createAccount(@Body createAccountModel: CreateAccountModel): Call<CreateAccountResponse>

    @POST("/push/token/save")
    fun storeFirebaseToken(@Body tokenRequest: AccountModel): Call<ResponseBody>

    @POST("/push/notification/action")
    fun logNotificationAction(@Body actionRequest: ActionRequest): Call<ResponseBody>

    @POST("/push/notification/event")
    fun logEvent(@Body eventLog: EventLogRequest): Call<ResponseBody>

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
