package band.effective.headlines.compose.main.di

import android.app.Activity
import band.effective.headlines.compose.MainActivity
import band.effective.headlines.compose.core.di.featureComponent
import band.effective.headlines.compose.core.di.findComponentDependencies
import band.effective.headlines.compose.core.di.scope.AppScope
import band.effective.headlines.compose.drawer.DrawerDependencies
import band.effective.headlines.compose.drawer.DrawerModule
import dagger.Component

val mainComponent = featureComponent<MainComponent, Activity> { activity ->
    DaggerMainComponent.factory().create(activity.findComponentDependencies())
}

@Component(
    modules = [DrawerModule::class],
    dependencies = [MainComponentDependencies::class]
)
@AppScope
interface MainComponent : DrawerDependencies {

    @Component.Factory
    interface Factory {

        fun create(deps: MainComponentDependencies): MainComponent
    }

    fun inject(activity: MainActivity)
}
