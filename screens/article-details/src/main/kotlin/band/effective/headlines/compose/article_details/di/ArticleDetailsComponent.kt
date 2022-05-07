package band.effective.headlines.compose.article_details.di

import android.app.Activity
import band.effective.headlines.compose.article_details.di.modules.ArticleDetailsViewModelModule
import band.effective.headlines.compose.article_details.presentation.ArticleDetailsViewModel
import band.effective.headlines.compose.core.di.featureComponent
import band.effective.headlines.compose.core.di.findComponentDependencies
import band.effective.headlines.compose.core.di.modules.ViewModelModule
import band.effective.headlines.compose.core.di.scope.FeatureScope
import dagger.Component

internal val articleDetailsComponent = featureComponent<ArticleDetailsComponent, Activity> { activity ->
    DaggerArticleDetailsComponent.factory().create(activity.findComponentDependencies())
}

@FeatureScope
@Component(
    modules = [ViewModelModule::class, ArticleDetailsViewModelModule::class],
    dependencies = [ArticleDetailsDependencies::class]
)
internal interface ArticleDetailsComponent {

    @Component.Factory
    interface Factory {
        fun create(deps: ArticleDetailsDependencies): ArticleDetailsComponent
    }

    val articleDetailsViewModelFactory: ArticleDetailsViewModel.Factory
}
