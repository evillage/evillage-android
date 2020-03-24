package nl.worth.clangnotifications.data.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import nl.worth.clangnotifications.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * INSERT CLASS DESCRIPTION HERE
 */
object ClangApiClient {

    @Volatile
    private var instance: ClangApiService? = null

    /**
     * Returns the same instance of the class if previously created, else it returns a new instance
     */
    fun getInstance(): ClangApiService {
        return if (instance == null) {
            return createApi()
        } else {
            instance as ClangApiService
        }
    }

    /**
     * Creates an instance of the [ClangApiService] using a configured [OkHttpClient] and [Gson] as JSON adapter
     */
    private fun createApi(): ClangApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.REMOTE_REPO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(createGsonAdapter()))
            .client(createOkHttpClient())
            .build()
        return retrofit.create(ClangApiService::class.java)
    }

    /**
     * Creates an instance of an [OkHttpClient] configured with custom connect, read and write timeouts
     * Also logs API calls to logcat if on DEBUG mode
     */
    private fun createOkHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Creates an instance of [Gson] JSON adapter to parse API responses
     */
    private fun createGsonAdapter(): Gson {
        return GsonBuilder()
            .create()
    }

}