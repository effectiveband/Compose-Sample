package band.effective.headlines.compose.navigation

import band.effective.headlines.compose.about.presentation.destinations.AboutScreenDestination
import band.effective.headlines.compose.article_details.presentation.destinations.ArticleDetailsScreenDestination
import band.effective.headlines.compose.feed.presentation.destinations.FeedScreenDestination
import band.effective.headlines.compose.search.presentation.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

object NavGraphs {

    val feed = object : NavGraphSpec {
        override val route = "feed"
        override val startRoute = FeedScreenDestination routedIn this
        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            FeedScreenDestination,
            ArticleDetailsScreenDestination
        ).routedIn(this).associateBy { it.route }
    }

    val search = object : NavGraphSpec {
        override val route = "search"
        override val startRoute = SearchScreenDestination routedIn this
        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            SearchScreenDestination,
            ArticleDetailsScreenDestination
        ).routedIn(this).associateBy { it.route }
    }

    val about = object : NavGraphSpec {
        override val route = "about"
        override val startRoute = AboutScreenDestination routedIn this
        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            AboutScreenDestination
        ).routedIn(this).associateBy { it.route }
    }

    val root = object : NavGraphSpec {
        override val route = "root"
        override val startRoute = feed
        override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()
        override val nestedNavGraphs = listOf(feed, search, about)
    }
}
