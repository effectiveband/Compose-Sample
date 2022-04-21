package band.effective.headlines.compose.about.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AboutScreen() {
    val systemUiController = rememberSystemUiController()
    val surfaceColorWithScrim = MaterialTheme.colorScheme.surface.copy(0.8F)

    SideEffect {
        systemUiController.setStatusBarColor(surfaceColorWithScrim)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "About", modifier = Modifier.align(Alignment.Center))
    }
}
