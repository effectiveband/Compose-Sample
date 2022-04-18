package band.effective.headlines.compose.search.di

import band.effective.headlines.compose.core.di.CommonDependencies
import band.effective.headlines.compose.news_api.data.NewsRepository

interface SearchDependencies: CommonDependencies {
    val newsRepository: NewsRepository
}
