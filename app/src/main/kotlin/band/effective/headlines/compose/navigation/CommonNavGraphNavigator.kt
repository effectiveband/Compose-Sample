package band.effective.headlines.compose.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import band.effective.headlines.compose.article_details.presentation.ArticleDetailsScreenNavigation
import band.effective.headlines.compose.article_details.presentation.destinations.ArticleDetailsScreenDestination
import band.effective.headlines.compose.feed.presentation.FeedScreenNavigation
import band.effective.headlines.compose.feed.presentation.models.HeadlineNavArg
import band.effective.headlines.compose.navigation.models.mappers.asArticle
import band.effective.headlines.compose.search.presentation.SearchScreenNavigation
import band.effective.headlines.compose.search.presentation.models.SearchItemNavArg
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.NavGraphSpec

class CommonNavGraphNavigator(
    private val context: Context,
    private val navGraph: NavGraphSpec,
    private val navController: NavController
) : FeedScreenNavigation, SearchScreenNavigation, ArticleDetailsScreenNavigation {

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun openLinkInBrowser(url: String) {
        val intent =
            Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }

    override fun openArticleDetails(headlineNavArg: HeadlineNavArg) {
        navController.navigateTo(
            ArticleDetailsScreenDestination(article = headlineNavArg.asArticle()) within navGraph
        )
    }

    override fun openArticleDetails(searchItemNavArg: SearchItemNavArg) {
        navController.navigateTo(
            ArticleDetailsScreenDestination(article = searchItemNavArg.asArticle()) within navGraph
        )
    }
}
