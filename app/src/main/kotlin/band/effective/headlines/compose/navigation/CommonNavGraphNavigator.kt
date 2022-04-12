package band.effective.headlines.compose.navigation

import androidx.navigation.NavController
import band.effective.headlines.compose.feed.presentation.FeedScreenNavigation
import band.effective.headlines.compose.news_details.presentation.destinations.NewsDetailsScreenDestination
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.NavGraphSpec

class CommonNavGraphNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController
): FeedScreenNavigation {

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun openNewsDetails() {
        navController.navigateTo(NewsDetailsScreenDestination within navGraph)
    }
}