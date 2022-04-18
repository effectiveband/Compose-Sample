package band.effective.headlines.compose.search.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
private fun SearchScreen(viewModel: SearchViewModel, openArticle: () -> Unit) {
    val uiState by rememberStateWithLifecycle(viewModel.state)
    val articlesItems = uiState.searchResult.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val topPaddingModifier = Modifier
        .statusBarsPadding()
        .padding(top = 12.dp)

    LaunchedEffect(listState.isScrollInProgress) {
        focusManager.clearFocus()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchUiEffect.NavigateToArticle -> TODO()
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
                    .padding(horizontal = 32.dp)
                    .fillMaxSize(),
            ) {
                articlesItems.retry()
                //viewModel.sendEvent(SearchUiEvent.OnRetry)
            }
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
