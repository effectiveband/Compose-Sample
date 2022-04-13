package band.effective.headlines.compose.feed.presentation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import band.effective.headlines.compose.core_ui.components.ErrorMessageWithButton
import band.effective.headlines.compose.core_ui.components.FullScreenErrorMessage
import band.effective.headlines.compose.core_ui.di.daggerViewModel
import band.effective.headlines.compose.core_ui.di.rememberFlowWithLifecycle
import band.effective.headlines.compose.feed.di.feedComponent
import band.effective.headlines.compose.feed.presentation.models.HeadlineItemUi
import band.effective.headlines.compose.news_api.data.headlines.remote.models.NewsLoadException
import coil.compose.AsyncImage
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun FeedScreen(navigator: FeedScreenNavigation) {
    val activity = LocalContext.current as Activity
    FeedScreen(
        viewModel = daggerViewModel(factory = feedComponent.getInstance(activity).viewModelFactory),
        openArticle = navigator::openNewsDetails
    )
}

@Composable
private fun FeedScreen(viewModel: FeedViewModel, openArticle: () -> Unit) {
    val uiState by rememberFlowWithLifecycle(viewModel.uiState).collectAsState(FeedUiState.Empty)
    val feedItems = uiState.feed.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is FeedUiEffect.NavigateToArticle -> TODO()
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = uiState.isLoading),
        onRefresh = { viewModel.sendEvent(FeedUiEvent.OnRetry) },
        modifier = Modifier.fillMaxSize()
    ) {
        when (val refresh = feedItems.loadState.refresh) {
            LoadState.Loading -> {
                if (feedItems.itemCount == 0) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            is LoadState.NotLoading -> {
                if (feedItems.itemCount != 0) {
                    FeedList(
                        feedItems = feedItems,
                        onRetry = { viewModel.sendEvent(FeedUiEvent.OnRetry) }
                    )
                }
            }
            is LoadState.Error -> {
                val message = (refresh.error as? NewsLoadException)?.reason?.message
                FullScreenErrorMessage(message = message, modifier = Modifier.fillMaxSize()) {
                    viewModel.sendEvent(FeedUiEvent.OnRetry)
                }
            }
        }
    }
}

@Composable
private fun FeedList(
    feedItems: LazyPagingItems<HeadlineItemUi>,
    modifier: Modifier = Modifier,
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
        items(
            items = feedItems,
            key = { headline ->
                "${headline.title}-${headline.source}"
            }
        ) { item ->
            item?.let {
                HeadlineCard(headline = item)
            }
            feedItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        if (feedItems.itemCount != 0) {
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
private fun HeadlineCard(headline: HeadlineItemUi, modifier: Modifier = Modifier) {
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (headline.imageUrl != null) {
                AsyncImage(
                    model = headline.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Text(
            text = headline.title,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = headline.source, style = MaterialTheme.typography.bodyMedium)
                Icon(
                    imageVector = Icons.Default.Adjust,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(16.dp)
                )
                Text(text = headline.date, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


