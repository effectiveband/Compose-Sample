package band.effective.headlines.compose.article_details.presentation

import androidx.compose.runtime.Immutable
import band.effective.headlines.compose.article_details.R
import band.effective.headlines.compose.core_ui.UiText

@Immutable
internal data class ArticleDetailsUiState(
    val title: String = "",
    val source: String = "",
    val url: String = "",
    val imageUrl: String? = null,
    val date: String = "",
    val description: UiText = UiText.StringResource(R.string.no_description),
    val content: UiText = UiText.StringResource(R.string.no_content)
) {
    companion object {
        val Empty = ArticleDetailsUiState()
    }
}

internal sealed class ArticleDetailsUiEvent {
    object OnBack : ArticleDetailsUiEvent()
    class OnOpenLink(val url: String) : ArticleDetailsUiEvent()
}

internal sealed class ArticleDetailsUiEffect {
    object NavigateBack : ArticleDetailsUiEffect()
    class OpenLinkInBrowser(val url: String) : ArticleDetailsUiEffect()
}
