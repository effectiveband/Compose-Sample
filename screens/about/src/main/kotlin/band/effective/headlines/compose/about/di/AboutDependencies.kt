package band.effective.headlines.compose.about.di

import band.effective.headlines.compose.core.di.CommonDependencies
import band.effective.headlines.compose.core.di.InjectedKey
import javax.inject.Named

interface AboutDependencies: CommonDependencies {

    @Named(InjectedKey.Configuration.VERSION_NAME)
    fun getAppVersionName(): String
}
