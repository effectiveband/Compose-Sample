package band.effective.headlines.compose.feed.presentation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import band.effective.headlines.compose.article_details.shared.ArticleNavArg
import band.effective.headlines.compose.core.navigation.Destination

object FeedScreenDestination : Destination {

    override val baseRoute = "feed"

    override val screenRoute = baseRoute

    override val arguments: List<NamedNavArgument> = emptyList()
}

fun NavGraphBuilder.feedScreen(onOpenArticleDetails: (ArticleNavArg) -> Unit) {
    composable(
        route = FeedScreenDestination.screenRoute,
        arguments = FeedScreenDestination.arguments
    ) {
        FeedScreen(onOpenArticleDetails = onOpenArticleDetails)
    }
}
