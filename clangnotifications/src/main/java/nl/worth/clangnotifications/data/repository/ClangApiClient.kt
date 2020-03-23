package nl.worth.clangnotifications.data.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClangApiClient{

    @Volatile
    private var instance: ClangApiService? = null

    fun getInstance(): ClangApiService {
        return if (instance == null) {
            return provideApi()
        } else {
            instance as ClangApiService
        }
    }

     private fun provideApi(): ClangApiService {
         val retrofit = Retrofit.Builder()
             .baseUrl(BuildConfig.REMOTE_REPO_BASE_URL)
             .addConverterFactory(GsonConverterFactory.create(provideGsonAdapter()))
             .client(provideOkHttpClient())
             .build()

        return retrofit.create(ClangApiService::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun provideGsonAdapter(): Gson {
        return GsonBuilder()
            .create()
    }

}