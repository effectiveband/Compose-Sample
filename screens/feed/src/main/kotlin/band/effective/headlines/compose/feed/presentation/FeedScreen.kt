package band.effective.headlines.compose.feed.presentation

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import band.effective.headlines.compose.feed.R
import band.effective.headlines.compose.feed.di.feedComponent
import band.effective.headlines.compose.feed.presentation.models.HeadlineItemUi
import band.effective.headlines.compose.news_api.data.headlines.remote.models.NewsLoadException
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
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
        openArticle = navigator::openArticleDetails
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
        state = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing),
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
                HeadlineCard(headline = item, modifier = modifier.fillMaxWidth())
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
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val iconRotationState by animateFloatAsState(targetValue = if (isExpanded) 180F else 0F)
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        if (headline.imageUrl != null) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(headline.imageUrl)
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
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = headline.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(7F)
                )
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier
                        .rotate(iconRotationState)
                        .weight(1F)
                ) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                }
            }
            AnimatedVisibility(visible = isExpanded) {
                Text(
                    text = headline.description ?: stringResource(id = R.string.no_description),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
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
}
