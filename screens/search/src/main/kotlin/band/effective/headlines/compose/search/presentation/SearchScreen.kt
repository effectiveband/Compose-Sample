package band.effective.headlines.compose.search.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import band.effective.headlines.compose.core_ui.components.ErrorImage
import band.effective.headlines.compose.core_ui.components.ErrorMessageWithButton
import band.effective.headlines.compose.core_ui.components.FullScreenErrorMessage
import band.effective.headlines.compose.core_ui.components.PlaceholderImage
import band.effective.headlines.compose.core_ui.di.daggerViewModel
import band.effective.headlines.compose.core_ui.di.rememberFlowWithLifecycle
import band.effective.headlines.compose.news_api.data.headlines.remote.models.NewsLoadException
import band.effective.headlines.compose.search.R
import band.effective.headlines.compose.search.di.searchComponent
import band.effective.headlines.compose.search.presentation.models.SearchItemUi
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
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
    val uiState by rememberFlowWithLifecycle(viewModel.uiState).collectAsState(SearchUiState.Empty)
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
            when (val refresh = articlesItems.loadState.refresh) {
                LoadState.Loading -> {
                    if (articlesItems.itemCount == 0) {
                        Box(
                            modifier = Modifier
                                .then(topPaddingModifier)
                                .fillMaxSize()
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
                is LoadState.NotLoading -> {
                    if (articlesItems.itemCount != 0) {
                        ArticlesList(
                            articles = articlesItems,
                            listState = listState,
                            onRetry = { viewModel.sendEvent(SearchUiEvent.OnRetry) }
                        )
                    } else {
                        EmptySearch(
                            modifier = Modifier
                                .then(topPaddingModifier)
                                .padding(horizontal = 32.dp)
                                .fillMaxSize()
                        )
                    }
                }
                is LoadState.Error -> {
                    val message = (refresh.error as? NewsLoadException)?.reason?.message
                    FullScreenErrorMessage(
                        message = message, modifier = Modifier
                            .then(topPaddingModifier)
                            .padding(horizontal = 32.dp)
                            .fillMaxSize()
                    ) {
                        viewModel.sendEvent(SearchUiEvent.OnRetry)
                    }
                }
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

@Composable
private fun EmptySearch(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.search_hint),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ArticlesList(
    articles: LazyPagingItems<SearchItemUi>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(32.dp)
            )
        }
        items(
            items = articles,
            key = { article ->
                "${article.title}-${article.source}"
            }
        ) { item ->
            item?.let {
                ArticleCard(article = item, modifier = modifier.fillMaxWidth())
            }
            articles.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        if (articles.itemCount != 0) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val message = (loadState.refresh as LoadState.Error).error.message
                        ErrorMessageWithButton(
                            message = message,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            onRetry()
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val message = (loadState.append as LoadState.Error).error.message
                        ErrorMessageWithButton(
                            message = message,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            onRetry()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleCard(article: SearchItemUi, modifier: Modifier = Modifier) {
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        if (article.imageUrl != null) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            ) {
                when (painter.state) {
                    AsyncImagePainter.State.Empty -> {}
                    is AsyncImagePainter.State.Loading -> {
                        PlaceholderImage(modifier = Modifier.fillMaxSize())
                    }
                    is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                    is AsyncImagePainter.State.Error -> {
                        ErrorImage(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
        Text(
            text = article.title,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun SearchField(text: String, modifier: Modifier = Modifier, onType: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = { onType(it) },
        modifier = modifier,
        placeholder = { Text(text = stringResource(id = R.string.search)) },
        leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
