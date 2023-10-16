package band.effective.headlines.compose.about.presentation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import band.effective.headlines.compose.core.navigation.Destination

object AboutScreenDestination : Destination {

    override val baseRoute = "about"

    override val screenRoute = baseRoute

    override val arguments: List<NamedNavArgument> = emptyList()
}

fun NavGraphBuilder.aboutScreen(onOpenLinkInBrowser: (String) -> Unit) {
    composable(
        route = AboutScreenDestination.screenRoute,
        arguments = AboutScreenDestination.arguments
    ) {
        AboutScreen(onOpenLinkInBrowser = onOpenLinkInBrowser)
    }
}
