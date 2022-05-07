package band.effective.headlines.compose.di.modules

import android.app.Application
import android.content.Context
import band.effective.headlines.compose.BuildConfig
import band.effective.headlines.compose.core.di.InjectedKey
import band.effective.headlines.compose.core.di.scope.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
interface AppModule {

    companion object {

        @AppScope
        @Provides
        fun provideContext(app: Application): Context = app.applicationContext

        @JvmStatic
        @Provides
        @Named(InjectedKey.Configuration.VERSION_NAME)
        fun provideAppVersionName(): String = BuildConfig.VERSION_NAME
    }
}
