package band.effective.headlines.compose.network.interceptors

import band.effective.headlines.compose.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class NewsApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = BuildConfig.NEWS_API_KEY
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("X-Api-Key", apiKey)
            .build()
        return chain.proceed(newRequest)
    }
}
