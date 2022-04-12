package band.effective.headlines.compose.di.modules

import band.effective.headlines.compose.core.di.ComponentDependencies
import band.effective.headlines.compose.core.di.ComponentDependenciesKey
import band.effective.headlines.compose.di.AppComponent
import band.effective.headlines.compose.main.di.MainComponentDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ComponentDependenciesModule {

    @Binds
    @IntoMap
    @ComponentDependenciesKey(MainComponentDependencies::class)
    fun bindMainComponentDeps(appComponent: AppComponent): ComponentDependencies
}
