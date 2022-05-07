package band.effective.headlines.compose.about.di

import android.app.Activity
import band.effective.headlines.compose.about.di.modules.AboutViewModelModule
import band.effective.headlines.compose.core.di.featureComponent
import band.effective.headlines.compose.core.di.findComponentDependencies
import band.effective.headlines.compose.core.di.modules.ViewModelModule
import band.effective.headlines.compose.core.di.scope.FeatureScope
import band.effective.headlines.compose.core.di.viewmodel.DaggerViewModelFactory
import dagger.Component

internal val aboutComponent = featureComponent<AboutComponent, Activity> { activity ->
    DaggerAboutComponent.factory().create(activity.findComponentDependencies())
}

@FeatureScope
@Component(
    modules = [ViewModelModule::class, AboutViewModelModule::class],
    dependencies = [AboutDependencies::class]
)
internal interface AboutComponent {

    @Component.Factory
    interface Factory {
        fun create(deps: AboutDependencies): AboutComponent
    }

    val viewModelFactory: DaggerViewModelFactory
}
