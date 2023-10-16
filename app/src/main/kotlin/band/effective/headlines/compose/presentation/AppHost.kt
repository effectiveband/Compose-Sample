package band.effective.headlines.compose.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppHost(appState: AppState = rememberAppState()) {
    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setNavigationBarColor(color = Color.Transparent)
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                val currentSelectedItem by appState.currentBottomItemAsState()
                BottomNavigationBar(
                    destinations = appState.topLevelDestinations,
                    selectedItem = currentSelectedItem,
                    onNavigationSelected = appState::navigateToTopLevelDestination
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        AppNavHost(
            navController = appState.navController,
            modifier = Modifier.padding(innerPadding),
            onChangeBottomBarVisibility = appState::changeBottomBarVisibility
        )
    }
}
