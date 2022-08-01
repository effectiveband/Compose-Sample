package band.effective.headlines.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.drawer_location.LocationModule
import band.effective.headlines.compose.di.DaggerAppComponent
import effective.band.compose.drawer_modules.BuildModule
import effective.band.compose.drawer_modules.DeviceModule
import effective.band.compose.drawer_modules.design.DebugGridLayer
import effective.band.compose.drawer_modules.design.DebugGridStateConfig
import effective.band.compose.drawer_modules.design.DesignModule
import effective.band.compose.drawer_modules.leak.LeakCanaryModule
import effective.band.compose.drawer_modules.okhttp.OkHttpLoggerModule
import effective.band.compose.drawer_modules.retrofit.RetrofitModule
import effective.band.drawer_base.ActionsModule
import effective.band.drawer_base.DebugDrawerLayout
import effective.band.drawer_base.actions.ButtonAction
import effective.band.drawer_base.actions.SwitchAction

@Composable
fun ConfigureScreen(
    application: android.app.Application,
    bodyContent: @Composable (isDrawerOpen: Boolean) -> Unit
) {

    val debugRetrofitConfig =
        DaggerAppComponent.factory().create(application).getDebugDrawerState()
    val httpLogger = DaggerAppComponent.factory().create(application).getHttpLogger()

    val gridAlpha = LocalContentAlpha.current

    var debugGridLayerConfig: DebugGridStateConfig by remember {
        mutableStateOf(
            DebugGridStateConfig(
                isEnabled = false,
                alpha = gridAlpha
            )
        )
    }


    DebugDrawerLayout(
        modifier = Modifier.systemBarsPadding(),
        drawerModules = {
            val modulesModifier = Modifier
                .padding(4.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colors.surface)
            ActionsModule(
                title = "Actions",
                icon = { Icon(Icons.Filled.Settings, contentDescription = null) }) {
                ButtonAction(text = "Button 1") {

                }
                ButtonAction(text = "Button 2") {

                }
                SwitchAction(text = "Switch 1", isChecked = true, onChange = {})
                SwitchAction(text = "Switch 2", isChecked = false, onChange = {})
            }
            DesignModule(modulesModifier, config = debugGridLayerConfig) {
                debugGridLayerConfig = it
            }
            BuildModule(modulesModifier)
            DeviceModule(modulesModifier)
            RetrofitModule(
                modifier = modulesModifier,
                debugRetrofitConfig
            )
            OkHttpLoggerModule(modulesModifier, httpLogger)
            LeakCanaryModule(modulesModifier)
            LocationModule(modulesModifier)
        },
        bodyContent = {
            Box {
                bodyContent(true)
                DebugGridLayer(debugGridLayerConfig)
            }
        },
    )
}