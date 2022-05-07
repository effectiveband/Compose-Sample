package band.effective.headlines.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import band.effective.headlines.compose.main.di.mainComponent
import band.effective.headlines.compose.presentation.AppHost
import band.effective.headlines.compose.ui.theme.HeadlinesComposeTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        mainComponent.getInstance(this).inject(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HeadlinesComposeTheme {
                ProvideWindowInsets {
                    AppHost()
                }
            }
        }
    }
}
