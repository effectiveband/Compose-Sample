package band.effective.headlines.compose.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavControllerVisibleEntries
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.navigation.navigateTo

@OptIn(NavControllerVisibleEntries::class)
@Composable
fun AppHost() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val surfaceColorWithScrim = MaterialTheme.colorScheme.surface.copy(0.8F)
    val navigationBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    val visibleEntries by navController.visibleEntries.collectAsState()
    val isBottomNavigationBarVisible = visibleEntries.any { entry ->
        BottomNavigationItems.any { bottomItem ->
            bottomItem.screen.startRoute.route == entry.destination.route
        }
    }

    SideEffect {
        with(systemUiController) {
            setStatusBarColor(
                color = surfaceColorWithScrim,
                darkIcons = useDarkIcons
            )
            setNavigationBarColor(
                color = if (isBottomNavigationBarVisible) navigationBarColor else Color.Transparent,
                darkIcons = useDarkIcons
            )
        }
    }

    HeadlinesScaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomNavigationBarVisible,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                val currentSelectedItem by navController.currentBottomItemAsState()
                BottomNavigationBar(
                    selectedNavigation = currentSelectedItem,
                    onNavigationSelected = { selected ->
                        navController.navigateTo(selected) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    },
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
