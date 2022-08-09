package band.effective.headlines.compose

import android.app.Application
import band.effective.headlines.compose.core.di.ComponentDependenciesProvider
import band.effective.headlines.compose.core.di.HasComponentDependencies
import band.effective.headlines.compose.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class HeadlinesComposeApp : Application(), HasComponentDependencies {

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override fun onCreate() {
        super.onCreate()

        plantTimber()
        DaggerAppComponent.factory()
            .create(this)
            .inject(this)
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
