package band.effective.headlines.compose

import androidx.compose.runtime.Composable
import effective.band.compose.drawer_modules.okhttp.HttpLogger
import effective.band.compose.drawer_modules.retrofit.DebugRetrofitConfig

@Composable
fun ConfigureScreen(
    debugRetrofitConfig: DebugRetrofitConfig,
    httpLogger: HttpLogger, bodyContent: @Composable (isDrawerOpen: Boolean) -> Unit
) {
    bodyContent(false)
}