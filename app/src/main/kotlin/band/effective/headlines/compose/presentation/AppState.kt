package band.effective.headlines.compose.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import band.effective.headlines.compose.navigation.AboutTopLevelDestination
import band.effective.headlines.compose.navigation.HeadlinesTopLevelDestination
import band.effective.headlines.compose.navigation.SearchTopLevelDestination
import band.effective.headlines.compose.navigation.TopLevelDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Immutable
class AppState(val navController: NavHostController) {

    private val _isBottomBarVisible = MutableStateFlow(true)
    val isBottomBarVisible = _isBottomBarVisible.asStateFlow()

    val topLevelDestinations = listOf(
        HeadlinesTopLevelDestination,
        SearchTopLevelDestination,
        AboutTopLevelDestination
    )

    fun changeBottomBarVisibility(isVisible: Boolean) {
        _isBottomBarVisible.update { isVisible }
    }

    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    @Composable
    fun currentBottomItemAsState(): State<TopLevelDestination> {
        val selectedItem = remember {
            mutableStateOf<TopLevelDestination>(HeadlinesTopLevelDestination)
        }

        DisposableEffect(navController) {
            val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                when {
                    destination.hierarchy.any { it.route == HeadlinesTopLevelDestination.route } -> {
                        selectedItem.value = HeadlinesTopLevelDestination
                    }

                    destination.hierarchy.any { it.route == SearchTopLevelDestination.route } -> {
                        selectedItem.value = SearchTopLevelDestination
                    }

                    destination.hierarchy.any { it.route == AboutTopLevelDestination.route } -> {
                        selectedItem.value = AboutTopLevelDestination
                    }
                }
            }
            navController.addOnDestinationChangedListener(listener)

            onDispose { navController.removeOnDestinationChangedListener(listener) }
        }
        return selectedItem
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()): AppState {
    return remember(navController) { AppState(navController) }
}
