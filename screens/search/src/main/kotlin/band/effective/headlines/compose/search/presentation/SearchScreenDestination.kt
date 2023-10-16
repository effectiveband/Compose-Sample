package band.effective.headlines.compose.search.presentation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import band.effective.headlines.compose.article_details.shared.ArticleNavArg
import band.effective.headlines.compose.core.navigation.Destination

object SearchScreenDestination : Destination {

    override val baseRoute = "search"

    override val screenRoute = baseRoute

    override val arguments: List<NamedNavArgument> = emptyList()
}

fun NavGraphBuilder.searchScreen(onOpenArticleDetails: (ArticleNavArg) -> Unit) {
    composable(
        route = SearchScreenDestination.screenRoute,
        arguments = SearchScreenDestination.arguments
    ) {
        SearchScreen(onOpenArticleDetails = onOpenArticleDetails)
    }
}
