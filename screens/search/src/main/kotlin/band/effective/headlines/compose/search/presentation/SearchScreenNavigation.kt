package band.effective.headlines.compose.search.presentation

import band.effective.headlines.compose.search.presentation.models.SearchItemNavArg

interface SearchScreenNavigation {

    fun openArticleDetails(searchItemNavArg: SearchItemNavArg)
}
