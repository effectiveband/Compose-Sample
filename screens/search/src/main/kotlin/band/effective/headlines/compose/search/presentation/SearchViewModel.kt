package band.effective.headlines.compose.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import band.effective.headlines.compose.news_api.data.NewsRepository
import band.effective.headlines.compose.news_api.domain.models.ArticleDomain
import band.effective.headlines.compose.search.presentation.models.mappers.asSearchItemUI
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class SearchViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<SearchUiEffect>()
    val effect = _effect.asSharedFlow()

    fun sendEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnNews -> TODO()
            is SearchUiEvent.OnSearchType -> {
                _uiState.update { it.copy(isLoading = true, searchQuery = event.query) }
                loadSearchResult(event.query)
            }
            SearchUiEvent.OnRetry -> {
                _uiState.update { it.copy(isRefreshing = true) }
                loadSearchResult(_uiState.value.searchQuery)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun loadSearchResult(query: String) {
        val result = newsRepository.getEverythingPagedFlow(query)
            .debounce(300)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .mapLatest { data ->
                data.map(ArticleDomain::asSearchItemUI)
            }
        _uiState.update { it.copy(isLoading = false, isRefreshing = false, searchResult = result) }
    }
}
