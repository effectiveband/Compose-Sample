package band.effective.headlines.compose.network.interceptors

import band.effective.headlines.compose.network.BuildConfig
import effective.band.compose.drawer_modules.retrofit.DebugRetrofitConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class NewsApiKeyInterceptor @Inject constructor(
    private val debugRetrofitConfig: DebugRetrofitConfig
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = if (debugRetrofitConfig.currentEndpoint.name != "Prod") {
            BuildConfig.NEWS_API_KEY
        } else {
            BuildConfig.NEWS_STAGE_API_KEY
        }
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("X-Api-Key", apiKey)
            .build()
        return chain.proceed(newRequest)
    }
}
