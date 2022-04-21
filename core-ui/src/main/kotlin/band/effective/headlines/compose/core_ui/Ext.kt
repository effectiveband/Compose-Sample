package band.effective.headlines.compose.core_ui

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import kotlin.math.ln

fun LazyListScope.pagingLoadStateItem(
    loadState: CombinedLoadStates,
    loadingContent: (@Composable LazyItemScope.() -> Unit),
    errorContent: (@Composable LazyItemScope.(String?) -> Unit),
) {
    when(loadState.append) {
        LoadState.Loading -> {
            item(key = "loadingFooter", content = loadingContent)
        }
        is LoadState.Error -> {
            val message = (loadState.append as LoadState.Error).error.localizedMessage
            item(key = "errorFooter", content = { errorContent(message) })
        }
        is LoadState.NotLoading -> {}
    }
    when(loadState.prepend) {
        LoadState.Loading -> {
            item(key = "loadingHeader", content = loadingContent)
        }
        is LoadState.Error -> {
            val message = (loadState.append as LoadState.Error).error.localizedMessage
            item(key = "errorHeader", content = { errorContent(message) })
        }
        is LoadState.NotLoading -> {}
    }
}

fun ColorScheme.surfaceColorAtElevation(elevation: Dp): Color {
    if (elevation == 0.dp) return surface
    val alpha = ((4.5f * ln(elevation.value + 1)) + 2f) / 100f
    return surfaceTint.copy(alpha = alpha).compositeOver(surface)
}
