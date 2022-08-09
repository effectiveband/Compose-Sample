package band.effective.headlines.compose.network.di

import band.effective.compose.drawer_modules.retrofit.DebugRetrofitConfig
import band.effective.headlines.compose.core.di.scope.AppScope
import band.effective.headlines.compose.network.EitherNewsAdapterFactory
import band.effective.headlines.compose.network.interceptors.NewsApiKeyInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.addAdapter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
interface NetworkModule {

    companion object {

        @OptIn(ExperimentalStdlibApi::class)
        @Provides
        fun provideMoshi(): Moshi {
            return Moshi.Builder()
                .addAdapter(Rfc3339DateJsonAdapter().nullSafe())
                .build()
        }

        @Provides
        @IntoSet
        fun provideNewsApiKeyInterceptor(
            debugRetrofitConfig: DebugRetrofitConfig
        ): Interceptor = NewsApiKeyInterceptor(debugRetrofitConfig)

        @Provides
        @IntoSet
        fun provideLoggingInterceptor(): Interceptor {
            return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        @Provides
        @AppScope
        fun provideBaseOkHttpClient(
            interceptors: Set<@JvmSuppressWildcards Interceptor>
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .apply { interceptors.forEach(::addInterceptor) }
                .callTimeout(5, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @AppScope
        fun provideBaseRetrofit(
            client: OkHttpClient,
            debugRetrofitConfig: DebugRetrofitConfig,
            moshi: Moshi
        ): Retrofit {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(debugRetrofitConfig.currentEndpoint.url)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(EitherNewsAdapterFactory())
                .build()
        }
    }
}
