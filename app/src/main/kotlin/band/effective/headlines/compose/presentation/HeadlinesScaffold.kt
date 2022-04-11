package band.effective.headlines.compose.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ui.Scaffold

@Composable
fun HeadlinesScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { topBar() },
        bottomBar = { bottomBar() },
        content = content
    )
}
