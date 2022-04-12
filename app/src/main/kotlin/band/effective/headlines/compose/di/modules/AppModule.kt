package band.effective.headlines.compose.di.modules

import android.app.Application
import android.content.Context
import band.effective.headlines.compose.core.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
interface AppModule {

    companion object {

        @AppScope
        @Provides
        fun provideContext(app: Application): Context = app.applicationContext
    }
}
