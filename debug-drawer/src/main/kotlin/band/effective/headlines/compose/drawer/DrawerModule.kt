package band.effective.headlines.compose.drawer

import android.content.Context
import band.effective.headlines.compose.core.di.scope.AppScope
import band.effective.headlines.compose.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import effective.band.compose.drawer_modules.okhttp.HttpLogger
import effective.band.compose.drawer_modules.retrofit.DebugRetrofitConfig
import effective.band.compose.drawer_modules.retrofit.Endpoint
import okhttp3.Interceptor
import retrofit2.mock.NetworkBehavior

@Module
interface DrawerModule {

    companion object {

        @Provides
        @AppScope
        fun provideHttpLogger(context: Context): HttpLogger = HttpLogger(context)

        @Provides
        @IntoSet
        @AppScope
        fun provideHttpLoggerInterceptor(httpLogger: HttpLogger): Interceptor =
            httpLogger.interceptor

        @Provides
        @AppScope
        fun provideNetworkBehavior(): NetworkBehavior = NetworkBehavior.create()

        @Provides
        @IntoSet
        @AppScope
        fun provideNetworkBehaviorInterceptor(
            networkBehavior: NetworkBehavior
        ): Interceptor = NetworkBehaviorSimulatorInterceptor(networkBehavior)

        @Provides
        @AppScope
        fun provideDebugRetrofitConfig(
            context: Context,
            networkBehavior: NetworkBehavior
        ): DebugRetrofitConfig = DebugRetrofitConfig(
            context = context,
            endpoints = listOf(
                Endpoint(name = "Stage", url = BuildConfig.NEWS_STAGE_URL, isMock = false),
                Endpoint(name = "Prod", url = BuildConfig.NEWS_URL, isMock = false),
                Endpoint(name = "Mock", url = BuildConfig.NEWS_STAGE_URL, isMock = true)
            ),
            networkBehavior = networkBehavior
        )
    }
}
