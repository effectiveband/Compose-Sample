package band.effective.headlines.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import band.effective.headlines.compose.di.DaggerAppComponent
import band.effective.headlines.compose.main.di.mainComponent
import band.effective.headlines.compose.presentation.AppHost
import band.effective.headlines.compose.presentation.setOwners
import band.effective.headlines.compose.ui.theme.HeadlinesComposeTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        mainComponent.getInstance(this).inject(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val debugRetrofitConfig = DaggerAppComponent.factory().create(application).getDebugRetrofitDrawer()
        val httpLogger = DaggerAppComponent.factory().create(application).getHttpLogger()
        val composeView = ComposeView(this).apply {
            setContent {
                HeadlinesComposeTheme {
                    ProvideWindowInsets {
                        ConfigureScreen(debugRetrofitConfig, httpLogger) {
                            AppHost()
                        }
                    }
                }
            }
        }
        setOwners()
        setContentView(composeView)
    }
}
