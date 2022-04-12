package band.effective.headlines.compose.news_api.di.modules

import band.effective.headlines.compose.core.di.scope.AppScope
import band.effective.headlines.compose.network.di.NewsNetwork
import band.effective.headlines.compose.news_api.data.NewsRepository
import band.effective.headlines.compose.news_api.data.NewsRepositoryImpl
import band.effective.headlines.compose.news_api.data.headlines.remote.NewsApiDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
internal interface NewsApiModule {

    companion object {

        @Provides
        fun provideApiDataSource(
            @NewsNetwork retrofit: Retrofit
        ): NewsApiDataSource = retrofit.create(NewsApiDataSource::class.java)
    }

    @Binds
    fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}
