package band.effective.headlines.compose.feed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import band.effective.headlines.compose.core_ui.R
import band.effective.headlines.compose.core_ui.components.ErrorMessageWithButton
import band.effective.headlines.compose.core_ui.pagingLoadStateItem
import band.effective.headlines.compose.feed.presentation.models.HeadlineItemUi
import com.google.accompanist.insets.statusBarsPadding

@Composable
internal fun FeedList(
    feedItems: LazyPagingItems<HeadlineItemUi>,
    modifier: Modifier = Modifier,
    openArticle: (HeadlineItemUi) -> Unit,
    onRetry: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Spacer(modifier = Modifier.statusBarsPadding())
        }
        pagingLoadStateItem(
            loadState = feedItems.loadState,
            loadingContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            },
            errorContent = { message ->
                ErrorMessageWithButton(
                    message = message  ?: stringResource(id = R.string.unknown_error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    onRetry()
                }
            }
        )
        itemsIndexed(items = feedItems) { _, item ->
            item?.let {
                HeadlineCard(headline = item, modifier = modifier.fillMaxWidth()) {
                    openArticle(it)
                }
            }
        }
        pagingLoadStateItem(
            loadState = feedItems.loadState,
            loadingContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            },
            errorContent = { message ->
                ErrorMessageWithButton(
                    message = message  ?: stringResource(id = R.string.unknown_error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    onRetry()
                }
            }
        )
    }
}
