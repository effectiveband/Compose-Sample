package band.effective.headlines.compose.di.modules

import band.effective.headlines.compose.core.di.ComponentDependencies
import band.effective.headlines.compose.core.di.ComponentDependenciesKey
import band.effective.headlines.compose.di.AppComponent
import band.effective.headlines.compose.feed.di.FeedDependencies
import band.effective.headlines.compose.main.di.MainComponentDependencies
import band.effective.headlines.compose.news_api.data.NewsRepository
import band.effective.headlines.compose.news_api.di.NewsApiDependencies
import band.effective.headlines.compose.news_api.di.NewsApiFactory
import band.effective.headlines.compose.search.di.SearchDependencies
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ComponentDependenciesModule {

    companion object {

        @Provides
        fun provideNewsApi(appComponent: AppComponent): NewsRepository =
            NewsApiFactory.create(appComponent)
    }

    @Binds
    @IntoMap
    @ComponentDependenciesKey(MainComponentDependencies::class)
    fun bindMainComponentDeps(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(NewsApiDependencies::class)
    fun bindNewsApiDependencies(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(FeedDependencies::class)
    fun bindFeedDependencies(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(SearchDependencies::class)
    fun bindSearchDependencies(appComponent: AppComponent): ComponentDependencies
}
