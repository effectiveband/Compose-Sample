package band.effective.headlines.compose.core_ui.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.savedstate.SavedStateRegistryOwner
import band.effective.headlines.compose.core.di.viewmodel.DaggerViewModelFactory
import band.effective.headlines.compose.core.di.viewmodel.createSavedStateViewModelFactory

@Composable
inline fun <reified VM : ViewModel> daggerSavedStateViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    savedStateRegistryOwner: SavedStateRegistryOwner = LocalSavedStateRegistryOwner.current,
    crossinline viewModelProducer: (SavedStateHandle) -> VM,
): VM {
    return viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = createSavedStateViewModelFactory(
            owner = savedStateRegistryOwner,
            arguments = (savedStateRegistryOwner as? NavBackStackEntry)?.arguments,
            creator = viewModelProducer
        )
    )
}

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

class LambdaViewModelFactory(private val create: () -> ViewModel) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create() as T
    }
}
