package band.effective.headlines.compose.presentation

import band.effective.headlines.compose.presentation.destinations.AboutScreenDestination
import band.effective.headlines.compose.presentation.destinations.HeadlineDetailsScreenDestination
import band.effective.headlines.compose.presentation.destinations.HeadlinesScreenDestination
import band.effective.headlines.compose.presentation.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

object NavGraphs {

    val headlines = object : NavGraphSpec {
        override val route = "headlines"
        override val startRoute = HeadlinesScreenDestination routedIn this
        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            HeadlinesScreenDestination,
            HeadlineDetailsScreenDestination
        ).routedIn(this).associateBy { it.route }
    }

    val search = object : NavGraphSpec {
        override val route = "search"
        override val startRoute = SearchScreenDestination routedIn this
        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            SearchScreenDestination,
            HeadlineDetailsScreenDestination
        ).routedIn(this).associateBy { it.route }
    }

    val about = object : NavGraphSpec {
        override val route = "about"
        override val startRoute = AboutScreenDestination routedIn this
        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            AboutScreenDestination
        ).routedIn(this).associateBy { it.route }
    }

    val root = object :NavGraphSpec {
        override val route = "root"
        override val startRoute = headlines
        override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()
        override val nestedNavGraphs = listOf(headlines, search, about)
    }
}
