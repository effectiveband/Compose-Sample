package band.effective.headlines.compose.core_ui.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import band.effective.headlines.compose.core.di.viewmodel.DaggerViewModelFactory

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    factory: DaggerViewModelFactory
): VM {
    return viewModel(viewModelStoreOwner, factory = factory)
}

@Composable
inline fun <reified VM : ViewModel> argumentViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    crossinline viewModelCreator: () -> ViewModel
): VM {
    return viewModel(
        viewModelStoreOwner,
        factory = LambdaViewModelFactory { viewModelCreator() }
    )
}

class LambdaViewModelFactory(
    private val create: () -> ViewModel
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create() as T
    }
}
