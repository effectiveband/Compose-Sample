package band.effective.headlines.compose.article_details.screen.presentation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import band.effective.headlines.compose.article_details.screen.di.articleDetailsComponent
import band.effective.headlines.compose.article_details.screen.presentation.components.ArticleContent
import band.effective.headlines.compose.article_details.screen.presentation.components.ArticleFloatingActionButton
import band.effective.headlines.compose.article_details.screen.presentation.components.BackButton
import band.effective.headlines.compose.core_ui.di.daggerSavedStateViewModel
import band.effective.headlines.compose.core_ui.rememberStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ArticleDetailsScreen(onBackClick: () -> Unit, onOpenLinkInBrowser: (String) -> Unit) {
    val activity = LocalContext.current as Activity

    ArticleDetailsScreen(
        viewModel = daggerSavedStateViewModel {
            articleDetailsComponent.getInstance(activity).articleDetailsViewModelFactory.create(it)
        },
        onBack = onBackClick,
        openLinkInBrowser = onOpenLinkInBrowser
    )

    DisposableEffect(Unit) {
        onDispose {
            articleDetailsComponent.clearInstance()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleDetailsScreen(
    viewModel: ArticleDetailsViewModel,
    onBack: () -> Unit,
    openLinkInBrowser: (String) -> Unit
) {
    val state by rememberStateWithLifecycle(viewModel.state)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val surfaceColorWithScrim = MaterialTheme.colorScheme.surface.copy(0.8F)
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(surfaceColorWithScrim)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ArticleDetailsUiEffect.NavigateBack -> onBack()
                is ArticleDetailsUiEffect.OpenLinkInBrowser -> openLinkInBrowser(effect.url)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            ArticleContent(
                imageUrl = state.imageUrl,
                title = state.title,
                description = state.description.asString(),
                content = state.content.asString(),
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
            )
        }
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(start = 16.dp, top = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
        ) {
            viewModel.sendEvent(ArticleDetailsUiEvent.OnBack)
        }
        ArticleFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
                .navigationBarsPadding()
        ) {
            viewModel.sendEvent(ArticleDetailsUiEvent.OnOpenLink(state.url))
        }
    }
}
