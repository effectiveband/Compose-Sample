package band.effective.headlines.compose.feed.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import band.effective.headlines.compose.core_ui.R
import band.effective.headlines.compose.core_ui.components.ErrorMessageWithIconAndButton
import band.effective.headlines.compose.feed.presentation.models.HeadlineItemUi
import band.effective.headlines.compose.news_api.data.headlines.remote.models.NewsLoadException

@Composable
internal fun FeedListPagingHolder(
    feedItems: LazyPagingItems<HeadlineItemUi>,
    modifier: Modifier = Modifier,
    openArticle: (HeadlineItemUi) -> Unit,
    onRetry: () -> Unit
) {
    when(val refresh = feedItems.loadState.refresh) {
        LoadState.Loading -> {
            Box(modifier = modifier) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is LoadState.NotLoading -> {
            if (feedItems.itemCount != 0) {
                FeedList(feedItems = feedItems, openArticle = {openArticle(it)}, onRetry = onRetry)
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
    }
}
