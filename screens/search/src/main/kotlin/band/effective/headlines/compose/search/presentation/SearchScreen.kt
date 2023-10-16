package band.effective.headlines.compose.search.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import band.effective.headlines.compose.article_details.shared.ArticleNavArg
import band.effective.headlines.compose.core_ui.di.daggerViewModel
import band.effective.headlines.compose.core_ui.rememberStateWithLifecycle
import band.effective.headlines.compose.search.di.searchComponent
import band.effective.headlines.compose.search.presentation.components.ArticlesListPagingHolder
import band.effective.headlines.compose.search.presentation.components.EmptySearch
import band.effective.headlines.compose.search.presentation.components.SearchField
import band.effective.headlines.compose.search.presentation.models.asNavArg
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(onOpenArticleDetails: (ArticleNavArg) -> Unit) {
    val activity = LocalContext.current as Activity
    SearchScreen(
        viewModel = daggerViewModel(factory = searchComponent.getInstance(activity).viewModelFactory),
        onOpenArticleDetails = onOpenArticleDetails
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchScreen(
    viewModel: SearchViewModel,
    onOpenArticleDetails: (ArticleNavArg) -> Unit
) {
    val uiState by rememberStateWithLifecycle(viewModel.state)
    val articlesItems = uiState.searchResult.collectAsLazyPagingItems()
    val surfaceColorWithScrim = MaterialTheme.colorScheme.surface.copy(0.8F)
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = { viewModel.sendEvent(SearchUiEvent.OnRetry) }
    )
    val focusManager = LocalFocusManager.current
    val topPaddingModifier = Modifier
        .statusBarsPadding()
        .padding(top = 12.dp)

    SideEffect {
        systemUiController.setStatusBarColor(surfaceColorWithScrim)
    }

    LaunchedEffect(listState.isScrollInProgress) {
        focusManager.clearFocus()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchUiEffect.NavigateToArticle -> onOpenArticleDetails(effect.article)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            if (uiState.searchQuery.isNotEmpty()) {
                ArticlesListPagingHolder(
                    articlesItems = articlesItems,
                    listState = listState,
                    modifier = Modifier
                        .then(topPaddingModifier)
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    openArticle = {
                        viewModel.sendEvent(SearchUiEvent.OnNews(it.asNavArg()))
                    },
                    onRetry = {
                        articlesItems.retry()
                    }
                )
            } else {
                EmptySearch(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            }
            PullRefreshIndicator(
                refreshing = uiState.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(
                        WindowInsets.displayCutout
                            .asPaddingValues()
                            .calculateTopPadding()
                    )
                    .padding(top = 72.dp)
            )
        }
        SearchField(
            text = uiState.searchQuery,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onType = {
                viewModel.sendEvent(SearchUiEvent.OnSearchType(it))
                coroutineScope.launch {
                    listState.scrollToItem(0, 0)
                }
            }
        )
    }
}
