package band.effective.headlines.compose.feed.presentation

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.collectAsLazyPagingItems
import band.effective.headlines.compose.core_ui.di.daggerViewModel
import band.effective.headlines.compose.core_ui.rememberStateWithLifecycle
import band.effective.headlines.compose.feed.di.feedComponent
import band.effective.headlines.compose.feed.presentation.components.FeedListPagingHolder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun FeedScreen(navigator: FeedScreenNavigation) {
    val activity = LocalContext.current as Activity
    FeedScreen(
        viewModel = daggerViewModel(factory = feedComponent.getInstance(activity).viewModelFactory),
        openArticle = navigator::openArticleDetails
    )
}

@Composable
private fun FeedScreen(viewModel: FeedViewModel, openArticle: () -> Unit) {
    val uiState by rememberStateWithLifecycle(viewModel.state)
    val feedItems = uiState.feed.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is FeedUiEffect.NavigateToArticle -> TODO()
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing),
        onRefresh = { viewModel.sendEvent(FeedUiEvent.OnRetry) },
        modifier = Modifier.fillMaxSize()
    ) {
        FeedListPagingHolder(
            feedItems = feedItems,
            modifier = Modifier.fillMaxSize(),
        ) {
            feedItems.retry()
        }
    }
}
