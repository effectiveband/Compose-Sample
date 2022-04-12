package band.effective.headlines.compose.main.di

import android.app.Activity
import band.effective.headlines.compose.MainActivity
import band.effective.headlines.compose.core.di.featureComponent
import band.effective.headlines.compose.core.di.findComponentDependencies
import dagger.Component

val mainComponent = featureComponent<MainComponent, Activity> { activity ->
    DaggerMainComponent.factory().create(activity.findComponentDependencies())
}

@Component(
    dependencies = [MainComponentDependencies::class]
)
interface MainComponent {

    @Component.Factory
    interface Factory {

        fun create(deps: MainComponentDependencies): MainComponent
    }

    fun inject(activity: MainActivity)
}
