package band.effective.headlines.compose.core_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun FadePlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.placeholder(
            visible = true,
            highlight = PlaceholderHighlight.fade()
        )
    )
}
