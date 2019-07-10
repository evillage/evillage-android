package nl.worth.clangnotifications.data.repository

import nl.worth.clangnotifications.BuildConfig
import nl.worth.clangnotifications.data.model.CreateAccountRequest
import nl.worth.clangnotifications.data.model.CreateAccountResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteRepository {

    @POST("account/create")
    fun createAccount(@Body request: CreateAccountRequest): Call<CreateAccountResponse>

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