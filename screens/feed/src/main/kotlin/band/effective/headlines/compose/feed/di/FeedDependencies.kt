package band.effective.headlines.compose.feed.di

import band.effective.headlines.compose.core.di.CommonDependencies
import band.effective.headlines.compose.news_api.data.NewsRepository

interface FeedDependencies: CommonDependencies {
    val newsRepository: NewsRepository
}
