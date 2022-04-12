package band.effective.headlines.compose

import android.app.Application
import band.effective.headlines.compose.core.di.ComponentDependenciesProvider
import band.effective.headlines.compose.core.di.HasComponentDependencies
import band.effective.headlines.compose.di.DaggerAppComponent
import javax.inject.Inject

class HeadlinesComposeApp : Application(), HasComponentDependencies {

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory()
            .create(this)
            .inject(this)
    }
}
