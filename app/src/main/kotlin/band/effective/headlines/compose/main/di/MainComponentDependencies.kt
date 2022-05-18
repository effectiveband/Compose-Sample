package band.effective.headlines.compose.main.di

import band.effective.headlines.compose.core.di.CommonDependencies
import band.effective.headlines.compose.core.di.ContentViewSetter

interface MainComponentDependencies : CommonDependencies {
    fun provideContentViewSetter(): ContentViewSetter
}
