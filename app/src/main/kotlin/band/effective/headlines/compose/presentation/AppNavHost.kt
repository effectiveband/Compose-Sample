package band.effective.headlines.compose.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import band.effective.headlines.compose.navigation.HeadlinesTopLevelDestination
import band.effective.headlines.compose.navigation.aboutGraph
import band.effective.headlines.compose.navigation.headlinesGraph
import band.effective.headlines.compose.navigation.searchGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onChangeBottomBarVisibility: (Boolean) -> Unit
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = HeadlinesTopLevelDestination.route,
        modifier = modifier
    ) {
        headlinesGraph(
            navController = navController,
            onOpenLinkInBrowser = context::openBrowserLink,
            onChangeBottomBarVisibility = onChangeBottomBarVisibility
        )
        searchGraph(
            navController = navController,
            onOpenLinkInBrowser = context::openBrowserLink,
            onChangeBottomBarVisibility = onChangeBottomBarVisibility
        )
        aboutGraph(onOpenLinkInBrowser = context::openBrowserLink)
    }
}
