package band.effective.headlines.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import band.effective.headlines.compose.about.presentation.AboutScreenDestination
import band.effective.headlines.compose.about.presentation.aboutScreen
import band.effective.headlines.compose.article_details.screen.presentation.articleDetailsScreen
import band.effective.headlines.compose.article_details.screen.presentation.navigateToArticleDetails
import band.effective.headlines.compose.feed.presentation.FeedScreenDestination
import band.effective.headlines.compose.feed.presentation.feedScreen
import band.effective.headlines.compose.search.presentation.SearchScreenDestination
import band.effective.headlines.compose.search.presentation.searchScreen

fun NavGraphBuilder.headlinesGraph(
    navController: NavController,
    onOpenLinkInBrowser: (String) -> Unit,
    onChangeBottomBarVisibility: (Boolean) -> Unit
) {
    navigation(
        startDestination = FeedScreenDestination.screenRoute,
        route = HeadlinesTopLevelDestination.route
    ) {
        feedScreen(
            onOpenArticleDetails = {
                onChangeBottomBarVisibility(false)
                navController.navigateToArticleDetails(
                    article = it,
                    graphRoute = HeadlinesTopLevelDestination.route
                )
            }
        )
        articleDetailsScreen(
            graphRoute = HeadlinesTopLevelDestination.route,
            onBackClick = {
                navController.navigateUp()
                onChangeBottomBarVisibility(true)
            },
            onOpenLinkInBrowser = onOpenLinkInBrowser
        )
    }
}

fun NavGraphBuilder.searchGraph(
    navController: NavController,
    onOpenLinkInBrowser: (String) -> Unit,
    onChangeBottomBarVisibility: (Boolean) -> Unit
) {
    navigation(
        startDestination = SearchScreenDestination.screenRoute,
        route = SearchTopLevelDestination.route
    ) {
        searchScreen(
            onOpenArticleDetails = {
                onChangeBottomBarVisibility(false)
                navController.navigateToArticleDetails(
                    article = it,
                    graphRoute = SearchTopLevelDestination.route
                )
            }
        )
        articleDetailsScreen(
            graphRoute = SearchTopLevelDestination.route,
            onBackClick = {
                navController.navigateUp()
                onChangeBottomBarVisibility(true)
            },
            onOpenLinkInBrowser = onOpenLinkInBrowser
        )
    }
}

fun NavGraphBuilder.aboutGraph(onOpenLinkInBrowser: (String) -> Unit) {
    navigation(
        startDestination = AboutScreenDestination.screenRoute,
        route = AboutTopLevelDestination.route
    ) {
        aboutScreen(onOpenLinkInBrowser = onOpenLinkInBrowser)
    }
}
