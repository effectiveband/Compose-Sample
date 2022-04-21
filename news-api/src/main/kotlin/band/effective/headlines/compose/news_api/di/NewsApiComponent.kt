package band.effective.headlines.compose.news_api.di

import band.effective.headlines.compose.core.di.scope.AppScope
import band.effective.headlines.compose.news_api.data.NewsRepository
import band.effective.headlines.compose.news_api.di.modules.NewsApiModule
import dagger.Component

@AppScope
@Component(
    modules = [NewsApiModule::class],
    dependencies = [NewsApiDependencies::class]
)
internal interface NewsApiComponent {

    @Component.Factory
    interface Factory {
        fun create(deps: NewsApiDependencies): NewsApiComponent
    }

    val repository: NewsRepository
}
