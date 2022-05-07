package band.effective.headlines.compose.about.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.headlines.compose.core.di.InjectedKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

internal class AboutViewModel @Inject constructor(
    @Named(InjectedKey.Configuration.VERSION_NAME) private val appVersion: String,
) : ViewModel() {

    private val _state = MutableStateFlow(AboutUiState.Empty)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AboutUiEffect>()
    val effect = _effect.asSharedFlow()

    init {
        obtainAppInformation()
    }

    fun sendEvent(event: AboutUiEvent) {
        when(event) {
            is AboutUiEvent.OnOpenLink -> {
                viewModelScope.launch {
                    _effect.emit(AboutUiEffect.OpenLinkInBrowser("https://newsapi.org/"))
                }
            }
        }
    }

    private fun obtainAppInformation() {
        _state.update { it.copy(appVersion = appVersion) }
    }
}
