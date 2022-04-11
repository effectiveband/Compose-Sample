package band.effective.headlines.compose.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material.icons.outlined.Feed
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Weekend
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import band.effective.headlines.compose.R
import band.effective.headlines.compose.presentation.destinations.AboutScreenDestination
import band.effective.headlines.compose.presentation.destinations.Destination
import band.effective.headlines.compose.presentation.destinations.HeadlinesScreenDestination
import band.effective.headlines.compose.presentation.destinations.SearchScreenDestination
import band.effective.headlines.compose.ui.theme.NoRippleTheme
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec

@Composable
fun BottomNavigationBar(
    selectedNavigation: NavGraphSpec,
    onNavigationSelected: (NavGraphSpec) -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        NavigationBar(modifier = modifier) {
            BottomNavigationItems.forEach { destination ->
                NavigationBarItem(
                    selected = selectedNavigation == destination.screen,
                    onClick = { onNavigationSelected(destination.screen) },
                    icon = {
                           BottomNavigationItemIcon(
                               item = destination,
                               selected = selectedNavigation == destination.screen
                           )
                    },
                    label = { Text(text = stringResource(id = destination.labelResId)) }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationItemIcon(item: BottomNavigationItem, selected: Boolean) {
    val painter = when (item) {
        is BottomNavigationItem.ResourceIcon -> painterResource(item.iconResId)
        is BottomNavigationItem.ImageVectorIcon -> rememberVectorPainter(item.iconImageVector)
    }
    val selectedPainter = when (item) {
        is BottomNavigationItem.ResourceIcon -> item.selectedIconResId?.let { painterResource(it) }
        is BottomNavigationItem.ImageVectorIcon -> item.selectedImageVector?.let { rememberVectorPainter(it) }
    }

    if (selectedPainter != null) {
        Crossfade(targetState = selected) {
            androidx.compose.material.Icon(
                painter = if (it) selectedPainter else painter,
                contentDescription = stringResource(item.contentDescriptionResId),
            )
        }
    } else {
        androidx.compose.material.Icon(
            painter = painter,
            contentDescription = stringResource(item.contentDescriptionResId),
        )
    }
}

sealed class BottomNavigationItem(
    val screen: NavGraphSpec,
    @StringRes val labelResId: Int,
    @StringRes val contentDescriptionResId: Int,
) {
    class ResourceIcon(
        screen: NavGraphSpec,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null,
    ) : BottomNavigationItem(screen, labelResId, contentDescriptionResId)

    class ImageVectorIcon(
        screen: NavGraphSpec,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        val iconImageVector: ImageVector,
        val selectedImageVector: ImageVector? = null,
    ) : BottomNavigationItem(screen, labelResId, contentDescriptionResId)
}

val BottomNavigationItems = listOf(
    BottomNavigationItem.ImageVectorIcon(
        screen = NavGraphs.headlines,
        labelResId = R.string.headlines,
        contentDescriptionResId = R.string.headlines,
        iconImageVector = Icons.Outlined.Feed,
        selectedImageVector = Icons.Default.Feed,
    ),
    BottomNavigationItem.ImageVectorIcon(
        screen = NavGraphs.search,
        labelResId = R.string.search,
        contentDescriptionResId = R.string.search,
        iconImageVector = Icons.Outlined.Search,
        selectedImageVector = Icons.Default.Search,
    ),
    BottomNavigationItem.ImageVectorIcon(
        screen = NavGraphs.about,
        labelResId = R.string.about,
        contentDescriptionResId = R.string.about,
        iconImageVector = Icons.Outlined.Info,
        selectedImageVector = Icons.Default.Info,
    )
)
