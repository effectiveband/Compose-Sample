package band.effective.headlines.compose.core.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType

abstract class DestinationNavType<T : Any?> : NavType<T>(true) {

    abstract fun serializeValue(value: T): String?

    abstract fun get(savedStateHandle: SavedStateHandle, key: String): T
}
