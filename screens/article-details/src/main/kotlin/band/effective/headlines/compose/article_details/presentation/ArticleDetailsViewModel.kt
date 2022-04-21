package band.effective.headlines.compose.article_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.headlines.compose.article_details.R
import band.effective.headlines.compose.article_details.presentation.destinations.ArticleDetailsScreenDestination
import band.effective.headlines.compose.core_ui.UiText
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ArticleDetailsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): ArticleDetailsViewModel
    }

    private val _state = MutableStateFlow(ArticleDetailsUiState.Empty)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ArticleDetailsUiEffect>()
    val effect = _effect.asSharedFlow()

    init {
        obtainArticle()
    }

    fun sendEvent(event: ArticleDetailsUiEvent) {
        when (event) {
            ArticleDetailsUiEvent.OnBack -> {
                viewModelScope.launch { _effect.emit(ArticleDetailsUiEffect.NavigateBack) }
            }
            is ArticleDetailsUiEvent.OnOpenLink -> {
                viewModelScope.launch {
                    _effect.emit(ArticleDetailsUiEffect.OpenLinkInBrowser(event.url))
                }
            }
        }
    }

    private fun obtainArticle() {
        val article = ArticleDetailsScreenDestination.argsFrom(savedStateHandle).article
        with(article) {
            _state.update {
                it.copy(
                    title = title,
                    source = source,
                    url = url,
                    imageUrl = imageUrl,
                    date = date,
                    description = if (description != null) {
                        UiText.DynamicString(description)
                    } else {
                        UiText.StringResource(R.string.no_description)
                    },
                    content = if (content != null) {
                        UiText.DynamicString(content)
                    } else {
                        UiText.StringResource(R.string.no_content)
                    }
                )
            }
        }
    }
}
