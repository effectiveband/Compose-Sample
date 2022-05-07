package band.effective.headlines.compose.feed.di

import android.app.Activity
import band.effective.headlines.compose.core.di.featureComponent
import band.effective.headlines.compose.core.di.findComponentDependencies
import band.effective.headlines.compose.core.di.modules.ViewModelModule
import band.effective.headlines.compose.core.di.scope.FeatureScope
import band.effective.headlines.compose.core.di.viewmodel.DaggerViewModelFactory
import band.effective.headlines.compose.feed.di.modules.FeedViewModelModule
import dagger.Component

internal val feedComponent = featureComponent<FeedComponent, Activity> { activity ->
    DaggerFeedComponent.factory().create(activity.findComponentDependencies())
}

@FeatureScope
@Component(
    modules = [ViewModelModule::class, FeedViewModelModule::class],
    dependencies = [FeedDependencies::class]
)
internal interface FeedComponent {

    @Component.Factory
    interface Factory {
        fun create(deps: FeedDependencies): FeedComponent
    }

    val viewModelFactory: DaggerViewModelFactory
}
