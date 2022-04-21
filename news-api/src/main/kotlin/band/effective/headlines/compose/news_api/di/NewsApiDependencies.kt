package band.effective.headlines.compose.news_api.di

import band.effective.headlines.compose.core.di.CommonDependencies
import band.effective.headlines.compose.core.di.ComponentDependencies
import band.effective.headlines.compose.network.di.NewsNetwork
import retrofit2.Retrofit

interface NewsApiDependencies : CommonDependencies {

    @NewsNetwork
    fun getRetrofit(): Retrofit
}
