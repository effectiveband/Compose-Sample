package band.effective.headlines.compose.core.di.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class DaggerViewModelFactory(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        return creator.get() as T
    }
}

inline fun <reified VM : ViewModel> SavedStateRegistryOwner.createSavedStateViewModelFactory(
    arguments: Bundle?,
    crossinline creator: (SavedStateHandle) -> VM,
): ViewModelProvider.Factory = object : AbstractSavedStateViewModelFactory(this, arguments) {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel?> create(
        key: String,
        modelClass: Class<VM>,
        handle: SavedStateHandle,
    ): VM = creator(handle) as VM
}
