package band.effective.headlines.compose.search.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import band.effective.headlines.compose.core_ui.components.ErrorMessageWithIconAndButton
import band.effective.headlines.compose.news_api.data.headlines.remote.models.NewsLoadException
import band.effective.headlines.compose.core_ui.R
import band.effective.headlines.compose.search.presentation.models.SearchItemUi

@Composable
internal fun ArticlesListPagingHolder(
    articlesItems: LazyPagingItems<SearchItemUi>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    openArticle: (SearchItemUi) -> Unit,
    onRetry: () -> Unit
) {
    when (val refresh = articlesItems.loadState.refresh) {
        is LoadState.NotLoading -> {
            if (articlesItems.itemCount != 0) {
                ArticlesList(
                    articles = articlesItems,
                    listState = listState,
                    openArticle = { openArticle(it) },
                    onRetry = onRetry
                )
            } else {
                EmptySearch(modifier = modifier)
            }
        }
        is LoadState.Error -> {
            val apiMessage = (refresh.error as? NewsLoadException)?.reason?.message
            ErrorMessageWithIconAndButton(
                message = apiMessage ?: stringResource(R.string.unknown_error),
                modifier = modifier,
                iconModifier = Modifier.size(160.dp)
            ) {
                onRetry()
            }
        }
        LoadState.Loading -> {
            Box(modifier = modifier) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
