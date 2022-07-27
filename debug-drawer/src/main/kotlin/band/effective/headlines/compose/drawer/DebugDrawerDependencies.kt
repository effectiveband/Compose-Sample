package band.effective.headlines.compose.drawer

import band.effective.headlines.compose.core.di.CommonDependencies
import effective.band.compose.drawer_modules.okhttp.HttpLogger
import effective.band.compose.drawer_modules.retrofit.DebugRetrofitConfig

interface DebugDrawerDependencies : CommonDependencies {

    fun getDebugRetrofitDrawer(): DebugRetrofitConfig
    fun getHttpLogger(): HttpLogger
}
