package band.effective.headlines.compose.feed.presentation

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import band.effective.headlines.compose.article_details.shared.ArticleNavArg
import band.effective.headlines.compose.feed.presentation.models.HeadlineItemUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
internal data class FeedUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val feed: Flow<PagingData<HeadlineItemUi>> = emptyFlow()
) {
    companion object {
        val Empty = FeedUiState()
    }
}

internal sealed class FeedUiEvent {
    data object OnRetry : FeedUiEvent()
    class OnHeadline(val article: ArticleNavArg) : FeedUiEvent()
}

internal sealed class FeedUiEffect {
    class NavigateToArticle(val article: ArticleNavArg) : FeedUiEffect()
}

