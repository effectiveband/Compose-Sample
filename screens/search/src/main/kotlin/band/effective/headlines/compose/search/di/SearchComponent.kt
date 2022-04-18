package band.effective.headlines.compose.search.di

import android.app.Activity
import band.effective.headlines.compose.core.di.featureComponent
import band.effective.headlines.compose.core.di.findComponentDependencies
import band.effective.headlines.compose.core.di.modules.ViewModelModule
import band.effective.headlines.compose.core.di.scope.FeatureScope
import band.effective.headlines.compose.core.di.viewmodel.DaggerViewModelFactory
import band.effective.headlines.compose.search.di.modules.SearchViewModelModule
import dagger.Component

val searchComponent = featureComponent<SearchComponent, Activity> { activity ->
    DaggerSearchComponent.factory().create(activity.findComponentDependencies())
}

@FeatureScope
@Component(
    modules = [ViewModelModule::class, SearchViewModelModule::class],
    dependencies = [SearchDependencies::class]
)
interface SearchComponent {

    @Component.Factory
    interface Factory {
        fun create(deps: SearchDependencies): SearchComponent
    }

    val viewModelFactory: DaggerViewModelFactory
}
