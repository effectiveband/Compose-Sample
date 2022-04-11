package band.effective.headlines.compose.about.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AboutScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "About", modifier = Modifier.align(Alignment.Center))
    }
}
