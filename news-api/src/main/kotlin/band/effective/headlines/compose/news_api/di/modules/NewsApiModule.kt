package band.effective.headlines.compose.news_api.di.modules

import band.effective.headlines.compose.news_api.data.NewsRepository
import band.effective.headlines.compose.news_api.data.NewsRepositoryImpl
import band.effective.headlines.compose.news_api.data.headlines.remote.NewsApiDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal interface NewsApiModule {

    companion object {

        @Provides
        fun provideApiDataSource(
            retrofit: Retrofit
        ): NewsApiDataSource = retrofit.create(NewsApiDataSource::class.java)
    }

    @Binds
    fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}
