package band.effective.headlines.compose.feed.presentation

import band.effective.headlines.compose.feed.presentation.models.HeadlineNavArg

interface FeedScreenNavigation {

    fun openArticleDetails(headlineNavArg: HeadlineNavArg)
}
