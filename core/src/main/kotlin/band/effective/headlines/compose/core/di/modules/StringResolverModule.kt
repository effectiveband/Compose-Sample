package band.effective.headlines.compose.core.di.modules

import android.content.Context
import band.effective.headlines.compose.core.di.string_resolver.StringResolver
import band.effective.headlines.compose.core.di.string_resolver.StringResolverImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface StringResolverModule {

    companion object {

        @Provides
        fun provideStringResolver(context: Context): StringResolver = StringResolverImpl(context)
    }
}
