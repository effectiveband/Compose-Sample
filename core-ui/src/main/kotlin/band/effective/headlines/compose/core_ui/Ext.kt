package band.effective.headlines.compose.core_ui

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

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
