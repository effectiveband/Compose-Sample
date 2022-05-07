package band.effective.headlines.compose.feed.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import band.effective.headlines.compose.core.di.viewmodel.DaggerViewModelFactory
import band.effective.headlines.compose.core.di.viewmodel.ViewModelKey
import band.effective.headlines.compose.feed.presentation.FeedViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
internal interface FeedViewModelModule {

    companion object {
        @Provides
        fun provideFeedViewModelFactory(
            creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory = DaggerViewModelFactory(creators)
    }

    @Binds
    @ViewModelKey(FeedViewModel::class)
    @IntoMap
    fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel
}
