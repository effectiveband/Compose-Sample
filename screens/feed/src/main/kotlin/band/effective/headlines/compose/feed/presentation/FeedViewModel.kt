package band.effective.headlines.compose.feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import band.effective.headlines.compose.feed.presentation.models.mappers.asHeadlineItemUI
import band.effective.headlines.compose.news_api.data.NewsRepository
import band.effective.headlines.compose.news_api.domain.models.ArticleDomain
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FeedViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FeedUiState.Empty)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<FeedUiEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadFeed()
    }

    fun sendEvent(event: FeedUiEvent) {
        when (event) {
            is FeedUiEvent.OnHeadline -> {
                viewModelScope.launch { _effect.emit(FeedUiEffect.NavigateToArticle(event.url)) }
            }
            FeedUiEvent.OnRetry -> {
                _state.update { it.copy(isRefreshing = true) }
                loadFeed()
            }
        }
    }

    private fun loadFeed() {
        val feed =
            newsRepository.getHeadlinesPagedFlow().cachedIn(viewModelScope).mapLatest { data ->
                data.map(ArticleDomain::asHeadlineItemUI)
            }
        _state.update { it.copy(isLoading = false, isRefreshing = false, feed = feed) }
    }
}
