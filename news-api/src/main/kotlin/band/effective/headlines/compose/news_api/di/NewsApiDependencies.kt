package band.effective.headlines.compose.news_api.di

import band.effective.headlines.compose.core.di.CommonDependencies
import retrofit2.Retrofit

interface NewsApiDependencies : CommonDependencies {

    fun getRetrofit(): Retrofit
}
