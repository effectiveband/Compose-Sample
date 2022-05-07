package band.effective.headlines.compose.search.presentation

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import band.effective.headlines.compose.search.presentation.models.SearchItemNavArg
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
    object OnRetry : SearchUiEvent()
    class OnSearchType(val query: String) : SearchUiEvent()
    class OnNews(val article: SearchItemNavArg) : SearchUiEvent()
}

internal sealed class SearchUiEffect {
    class NavigateToArticle(val article: SearchItemNavArg) : SearchUiEffect()
}
