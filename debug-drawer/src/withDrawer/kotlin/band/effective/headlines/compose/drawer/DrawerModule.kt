package band.effective.headlines.compose.drawer

import android.content.Context
import au.com.gridstone.debugdrawer.DebugDrawer
import au.com.gridstone.debugdrawer.DeviceInfoModule
import au.com.gridstone.debugdrawer.okhttplogs.HttpLogger
import au.com.gridstone.debugdrawer.okhttplogs.OkHttpLoggerModule
import au.com.gridstone.debugdrawer.retrofit.DebugRetrofitConfig
import au.com.gridstone.debugdrawer.retrofit.Endpoint
import au.com.gridstone.debugdrawer.retrofit.RetrofitModule
import au.com.gridstone.debugdrawer.timber.TimberModule
import band.effective.headlines.compose.core.di.ContentViewSetter
import band.effective.headlines.compose.core.di.scope.AppScope
import band.effective.headlines.compose.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import retrofit2.mock.NetworkBehavior

@Module
interface DrawerModule {

    companion object {
        @Provides
        fun provideContentViewSetter(
            httpLogger: HttpLogger,
            debugRetrofitConfig: DebugRetrofitConfig
        ): ContentViewSetter = ContentViewSetter { activity, view ->
            DebugDrawer.with(activity)
                .addSectionTitle("Network")
                .addModule(RetrofitModule(debugRetrofitConfig))
                .addSectionTitle("Logs")
                .addModule(OkHttpLoggerModule(httpLogger))
                .addModule(TimberModule())
                .addSectionTitle("Device information")
                .addModule(DeviceInfoModule())
                .buildMainContainer()
                .apply { addView(view) }
        }

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
