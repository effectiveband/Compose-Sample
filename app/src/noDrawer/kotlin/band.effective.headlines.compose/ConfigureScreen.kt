package band.effective.headlines.compose

import androidx.compose.runtime.Composable

@Composable
fun ConfigureScreen(
    bodyContent: @Composable (isDrawerOpen: Boolean) -> Unit
) {
    bodyContent(false)
}