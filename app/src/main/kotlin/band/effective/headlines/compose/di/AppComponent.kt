package band.effective.headlines.compose.di

import android.app.Application
import band.effective.headlines.compose.HeadlinesComposeApp
import band.effective.headlines.compose.MainActivity
import band.effective.headlines.compose.about.di.AboutDependencies
import band.effective.headlines.compose.article_details.di.ArticleDetailsDependencies
import band.effective.headlines.compose.core.di.CommonDependencies
import band.effective.headlines.compose.core.di.scope.AppScope
import band.effective.headlines.compose.di.modules.AppModule
import band.effective.headlines.compose.di.modules.ComponentDependenciesModule
import band.effective.headlines.compose.drawer.DrawerDependencies
import band.effective.headlines.compose.drawer.DrawerModule
import band.effective.headlines.compose.feed.di.FeedDependencies
import band.effective.headlines.compose.main.di.MainComponentDependencies
import band.effective.headlines.compose.network.di.NetworkModule
import band.effective.headlines.compose.news_api.di.NewsApiDependencies
import band.effective.headlines.compose.search.di.SearchDependencies
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        AppModule::class,
        DrawerModule::class,
        NetworkModule::class,
        ComponentDependenciesModule::class
    ]
)
interface AppComponent :
    CommonDependencies,
    MainComponentDependencies,
    NewsApiDependencies,
    FeedDependencies,
    SearchDependencies,
    ArticleDetailsDependencies,
    AboutDependencies,
    DrawerDependencies {


    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(app: HeadlinesComposeApp)
    fun inject(mainActivity: MainActivity)
}
