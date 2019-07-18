package nl.worth.clangnotifications.data.repository

import nl.worth.clangnotifications.BuildConfig
import nl.worth.clangnotifications.data.model.AccountModel
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteRepository {

    @POST("api/v1/account/register")
    fun createAccount(@Body accountModel: AccountModel): Call<CreateAccountResponse>

    companion object {
        fun create(): RemoteRepository {

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.REMOTE_REPO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RemoteRepository::class.java)
        }
    }

    @POST("/api/v1/token/save")
    fun storeFirebaseToken(@Body tokenRequest: AccountModel): Call<Unit>

}