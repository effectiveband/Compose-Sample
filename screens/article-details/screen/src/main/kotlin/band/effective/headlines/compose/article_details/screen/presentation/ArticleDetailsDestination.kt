package band.effective.headlines.compose.article_details.screen.presentation

import androidx.activity.compose.BackHandler
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import band.effective.headlines.compose.article_details.shared.ArticleNavArg
import band.effective.headlines.compose.core.navigation.Destination

object ArticleDetailsDestination : Destination {

    private const val articleArgumentKey = "article"

    override val baseRoute = "article_details"

    override val screenRoute = "$baseRoute/{$articleArgumentKey}"

    override val arguments = listOf(
        navArgument(articleArgumentKey) {
            type = articleNavType
        }
    )

    internal fun argsFrom(savedStateHandle: SavedStateHandle): ArticleNavArg {
        return articleNavType.get(savedStateHandle, articleArgumentKey)
            ?: throw RuntimeException("'article' argument is mandatory, but was not present!")
    }
}

fun NavController.navigateToArticleDetails(
    article: ArticleNavArg,
    graphRoute: String? = null,
    navOptions: NavOptions? = null
) {
    val serialized = articleNavType.serializeValue(article)
    val resultRoute = if (graphRoute != null) {
        "$graphRoute/${ArticleDetailsDestination.baseRoute}/$serialized"
    } else {
        "${ArticleDetailsDestination.baseRoute}/$serialized"
    }
    navigate(resultRoute, navOptions)
}

fun NavGraphBuilder.articleDetailsScreen(
    graphRoute: String? = null,
    onBackClick: () -> Unit,
    onOpenLinkInBrowser: (String) -> Unit
) {
    val resultRoute = if (graphRoute != null) {
        "$graphRoute/${ArticleDetailsDestination.screenRoute}"
    } else {
        ArticleDetailsDestination.screenRoute
    }
    composable(
        route = resultRoute,
        arguments = ArticleDetailsDestination.arguments
    ) {
        BackHandler(onBack = onBackClick)

        ArticleDetailsScreen(onBackClick = onBackClick, onOpenLinkInBrowser = onOpenLinkInBrowser)
    }
}
