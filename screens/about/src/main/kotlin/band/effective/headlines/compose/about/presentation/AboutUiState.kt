package band.effective.headlines.compose.about.presentation

import androidx.compose.runtime.Immutable
import band.effective.headlines.compose.about.R
import band.effective.headlines.compose.core_ui.UiText

@Immutable
internal data class AboutUiState(
    val appName: UiText = UiText.StringResource(band.effective.headlines.compose.core_ui.R.string.app_name),
    val appVersion: String = "",
    val description: UiText = UiText.StringResource(R.string.app_description)
) {
    companion object {
        val Empty = AboutUiState()
    }
}

internal sealed class AboutUiEvent {
    object OnOpenLink : AboutUiEvent()
}

internal sealed class AboutUiEffect {
    class OpenLinkInBrowser(val url: String) : AboutUiEffect()
}
