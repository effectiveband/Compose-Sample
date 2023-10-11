package band.effective.headlines.compose.feed.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.collectAsLazyPagingItems
import band.effective.headlines.compose.article_details.shared.ArticleNavArg
import band.effective.headlines.compose.core_ui.di.daggerViewModel
import band.effective.headlines.compose.core_ui.rememberStateWithLifecycle
import band.effective.headlines.compose.feed.di.feedComponent
import band.effective.headlines.compose.feed.presentation.components.FeedListPagingHolder
import band.effective.headlines.compose.feed.presentation.models.asNavArg
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun FeedScreen(onOpenArticleDetails: (ArticleNavArg) -> Unit) {
    val activity = LocalContext.current as Activity

    FeedScreen(
        viewModel = daggerViewModel(factory = feedComponent.getInstance(activity).viewModelFactory),
        onOpenArticleDetails = onOpenArticleDetails
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FeedScreen(viewModel: FeedViewModel, onOpenArticleDetails: (ArticleNavArg) -> Unit) {
    val uiState by rememberStateWithLifecycle(viewModel.state)
    val feedItems = uiState.feed.collectAsLazyPagingItems()
    val surfaceColorWithScrim = MaterialTheme.colorScheme.surface.copy(0.8F)
    val systemUiController = rememberSystemUiController()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = { viewModel.sendEvent(FeedUiEvent.OnRetry) }
    )

    SideEffect {
        systemUiController.setStatusBarColor(surfaceColorWithScrim)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is FeedUiEffect.NavigateToArticle -> onOpenArticleDetails(effect.article)
            }
        }
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        FeedListPagingHolder(
            feedItems = feedItems,
            modifier = Modifier.fillMaxSize(),
            openArticle = {
                viewModel.sendEvent(FeedUiEvent.OnHeadline(it.asNavArg()))
            },
            onRetry = {
                feedItems.retry()
            }
        )
        PullRefreshIndicator(
            refreshing = uiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
