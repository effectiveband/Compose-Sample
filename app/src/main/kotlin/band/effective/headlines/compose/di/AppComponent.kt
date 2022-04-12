package band.effective.headlines.compose.di

import android.app.Application
import band.effective.headlines.compose.HeadlinesComposeApp
import band.effective.headlines.compose.MainActivity
import band.effective.headlines.compose.core.di.CommonDependencies
import band.effective.headlines.compose.core.di.modules.StringResolverModule
import band.effective.headlines.compose.core.di.scope.AppScope
import band.effective.headlines.compose.di.modules.AppModule
import band.effective.headlines.compose.di.modules.ComponentDependenciesModule
import band.effective.headlines.compose.main.di.MainComponentDependencies
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        AppModule::class,
        ComponentDependenciesModule::class,
        StringResolverModule::class
    ]
)
interface AppComponent : CommonDependencies, MainComponentDependencies {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(app: HeadlinesComposeApp)
    fun inject(mainActivity: MainActivity)
}
