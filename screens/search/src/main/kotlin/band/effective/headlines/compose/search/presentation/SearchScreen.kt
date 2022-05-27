package band.effective.headlines.compose.search.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import band.effective.headlines.compose.core_ui.di.daggerViewModel
import band.effective.headlines.compose.core_ui.rememberStateWithLifecycle
import band.effective.headlines.compose.search.di.searchComponent
import band.effective.headlines.compose.search.presentation.components.ArticlesListPagingHolder
import band.effective.headlines.compose.search.presentation.components.SearchField
import band.effective.headlines.compose.search.presentation.models.SearchItemNavArg
import band.effective.headlines.compose.search.presentation.models.asNavArg
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@Destination
@Composable
fun SearchScreen(navigator: SearchScreenNavigation) {
    val activity = LocalContext.current as Activity
    SearchScreen(
        viewModel = daggerViewModel(factory = searchComponent.getInstance(activity).viewModelFactory),
        openArticle = navigator::openArticleDetails
    )
}

@Composable
private fun SearchScreen(viewModel: SearchViewModel, openArticle: (SearchItemNavArg) -> Unit) {
    val uiState by rememberStateWithLifecycle(viewModel.state)
    val articlesItems = uiState.searchResult.collectAsLazyPagingItems()
    val surfaceColorWithScrim = MaterialTheme.colorScheme.surface.copy(0.8F)
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
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
                is SearchUiEffect.NavigateToArticle -> openArticle(effect.article)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing),
            onRefresh = { viewModel.sendEvent(SearchUiEvent.OnRetry) },
            indicatorPadding = PaddingValues(top = LocalWindowInsets.current.statusBars.top.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        ) {
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
        }
        SearchField(
            text = uiState.searchQuery,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp)
                .padding(horizontal = 32.dp)
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
