package band.effective.headlines.compose.network.di

import band.effective.headlines.compose.core.di.scope.AppScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.addAdapter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [NewsNetworkModule::class])
interface NetworkModule {

    companion object {

        @OptIn(ExperimentalStdlibApi::class)
        @Provides
        @AppScope
        fun provideMoshi(): Moshi {
            return Moshi.Builder()
                .addAdapter(Rfc3339DateJsonAdapter().nullSafe())
                .build()
        }

        @BaseNetwork
        @Provides
        @IntoSet
        fun provideLoggingInterceptor(): Interceptor {
            return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        @BaseNetwork
        @Provides
        @AppScope
        fun provideBaseOkHttpClient(
            @BaseNetwork interceptors: Set<@JvmSuppressWildcards Interceptor>
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .apply { interceptors.forEach(::addInterceptor) }
                .callTimeout(5, TimeUnit.SECONDS)
                .build()
        }

        @BaseNetwork
        @Provides
        @AppScope
        fun provideBaseRetrofit(
            @BaseNetwork client: OkHttpClient,
            moshi: Moshi
        ): Retrofit {
            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://base.url")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }
    }
}
