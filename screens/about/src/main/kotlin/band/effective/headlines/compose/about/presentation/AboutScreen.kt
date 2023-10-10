package band.effective.headlines.compose.about.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import band.effective.headlines.compose.about.di.aboutComponent
import band.effective.headlines.compose.about.presentation.components.AboutBody
import band.effective.headlines.compose.about.presentation.components.AboutFloatingActionButton
import band.effective.headlines.compose.core_ui.di.daggerViewModel
import band.effective.headlines.compose.core_ui.rememberStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AboutScreen(navigation: AboutScreenNavigation) {
    val activity = LocalContext.current as Activity

    AboutScreen(
        viewModel = daggerViewModel(factory = aboutComponent.getInstance(activity).viewModelFactory),
        onOpenLink = navigation::openLinkInBrowser
    )
}

@Composable
private fun AboutScreen(viewModel: AboutViewModel, onOpenLink: (String) -> Unit) {
    val state by rememberStateWithLifecycle(viewModel.state)
    val systemUiController = rememberSystemUiController()
    val surfaceColorWithScrim = MaterialTheme.colorScheme.surface.copy(0.8F)

    SideEffect {
        systemUiController.setStatusBarColor(surfaceColorWithScrim)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is AboutUiEffect.OpenLinkInBrowser -> onOpenLink(effect.url)
            }
        }
    }

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        AboutBody(
            appName = state.appName.asString(),
            appVersion = state.appVersion,
            description = state.description.asString(),
            modifier = Modifier.fillMaxSize()
        )
        AboutFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            viewModel.sendEvent(AboutUiEvent.OnOpenLink)
        }
    }
}
