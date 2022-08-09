package band.effective.headlines.compose.drawer

import band.effective.compose.drawer_modules.okhttp.HttpLogger
import band.effective.compose.drawer_modules.retrofit.DebugRetrofitConfig
import band.effective.headlines.compose.core.di.CommonDependencies

interface DrawerDependencies : CommonDependencies {

    fun getDebugDrawerState(): DebugRetrofitConfig
    fun getHttpLogger(): HttpLogger
}