package band.effective.headlines.compose.network.interceptors

import band.effective.headlines.compose.core.di.InjectedKey
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

internal class NewsApiKeyInterceptor @Inject constructor(
    @Named(InjectedKey.News.API_KEY) private val apiKey: String
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("X-Api-Key", apiKey)
            .build()
        return chain.proceed(newRequest)
    }
}
