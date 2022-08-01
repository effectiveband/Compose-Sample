package band.effective.headlines.compose

import androidx.compose.runtime.Composable

@Composable
fun ConfigureScreen(
    application: android.app.Application,
    bodyContent: @Composable (isDrawerOpen: Boolean) -> Unit
) {
    bodyContent(false)
}