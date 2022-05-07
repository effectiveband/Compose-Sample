package band.effective.headlines.compose.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import band.effective.headlines.compose.navigation.CommonNavGraphNavigator
import band.effective.headlines.compose.navigation.NavGraphs
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.spec.NavGraphSpec
import timber.log.Timber

@Stable
@Composable
fun NavController.currentBottomItemToState(): State<NavGraphSpec> {
    val selectedItem = remember { mutableStateOf(NavGraphs.feed) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            backQueue.print()
            selectedItem.value = destination.navGraph()
        }
        addOnDestinationChangedListener(listener)

        onDispose { removeOnDestinationChangedListener(listener) }
    }
    return selectedItem
}

fun NavDestination.navGraph(): NavGraphSpec {
    hierarchy.forEach { destination ->
        NavGraphs.root.nestedNavGraphs.forEach { navGraph ->
            if (destination.route == navGraph.route) {
                return navGraph
            }
        }
    }
    throw RuntimeException("Unknown nav graph for destination $route")
}

fun ArrayDeque<NavBackStackEntry>.print(prefix: String = "stack") {
    val stack = toMutableList()
        .map { it.destination.route }
        .toTypedArray().contentToString()
    Timber.d("Nav stack: $prefix = $stack")
}

fun DestinationScope<*>.currentNavigator(context: Context): CommonNavGraphNavigator {
    return CommonNavGraphNavigator(
        context,
        navBackStackEntry.destination.navGraph(),
        navController
    )
}
