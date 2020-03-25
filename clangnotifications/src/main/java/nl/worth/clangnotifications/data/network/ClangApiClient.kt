package nl.worth.clangnotifications.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import nl.worth.clangnotifications.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit

/**
 *  Creates an instance of [ClangApiService] as a Singleton
 */
object ClangApiClient {

    /**
     * Singleton instance of [ClangApiClient]
     */
    @Volatile
    private var instance: ClangApiService? = null

    /**
     * Value of the authorization header
     */
    private var authToken: String? = null

    /**
     * Initializes singleton instance of [ClangApiClient]
     *
     * @param authToken Value of authorization header
     */
    fun init(authToken: String) {
        this.authToken = authToken
    }

    /**
     * Returns the same instance of the class if previously created, else it returns a new instance
     */
    fun getService(): ClangApiService {
        if (authToken == null) throw NullPointerException("Authorization token is null, call init(authToken) at least one time passing the value for the authorization token")
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
            .addInterceptor(TokenAuthenticator(authToken!!))
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

    /**
     * Intercepts every API call and adds the Authorization header
     */
    class TokenAuthenticator(authToken: String) : Interceptor {
        private val authorizationHeader = "Bearer $authToken"

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request = request.newBuilder()
                .addHeader("authorization", authorizationHeader)
                .build()
            return chain.proceed(request)
        }
    }
}