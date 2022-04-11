package band.effective.headlines.compose.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import band.effective.headlines.compose.presentation.destinations.HeadlineDetailsScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.NavGraphSpec

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    DestinationsNavHost(
        navGraph = NavGraphs.root,
        navController = navController,
        modifier = modifier,
        dependenciesContainerBuilder = {
            dependency(currentNavigator())
        }
    )
}

class CommonNavGraphNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController
):HeadlinesScreenNavigation {

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun openHeadlineDetails() {
        navController.navigateTo(HeadlineDetailsScreenDestination within navGraph)
    }
}
