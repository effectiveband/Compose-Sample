package band.effective.headlines.compose.search.presentation

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import band.effective.headlines.compose.article_details.shared.ArticleNavArg
import band.effective.headlines.compose.search.presentation.models.SearchItemUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
internal data class SearchUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val searchResult: Flow<PagingData<SearchItemUi>> = emptyFlow()
) {
    companion object {
        val Empty = SearchUiState()
    }
}

internal sealed class SearchUiEvent {
    data object OnRetry : SearchUiEvent()
    class OnSearchType(val query: String) : SearchUiEvent()
    class OnNews(val article: ArticleNavArg) : SearchUiEvent()
}

internal sealed class SearchUiEffect {
    class NavigateToArticle(val article: ArticleNavArg) : SearchUiEffect()
}
