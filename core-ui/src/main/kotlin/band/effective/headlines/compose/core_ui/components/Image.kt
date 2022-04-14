package band.effective.headlines.compose.core_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun PlaceholderImage(modifier: Modifier) {
    Box(
        modifier = modifier.placeholder(
            visible = true,
            highlight = PlaceholderHighlight.fade()
        )
    )
}

@Composable
fun ErrorImage(modifier: Modifier) {
    Box(modifier = modifier) {
        Icon(
            imageVector = Icons.Outlined.BrokenImage,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(64.dp)
        )
    }
}
