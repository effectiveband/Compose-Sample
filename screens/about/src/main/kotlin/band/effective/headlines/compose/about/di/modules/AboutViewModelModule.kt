package band.effective.headlines.compose.about.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import band.effective.headlines.compose.about.presentation.AboutViewModel
import band.effective.headlines.compose.core.di.viewmodel.DaggerViewModelFactory
import band.effective.headlines.compose.core.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
internal interface AboutViewModelModule {

    companion object {

        @Provides
        fun provideAboutViewModelFactory(
            creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory = DaggerViewModelFactory(creators)
    }

    @Binds
    @ViewModelKey(AboutViewModel::class)
    @IntoMap
    fun bindAboutViewModel(viewModel: AboutViewModel): ViewModel
}
