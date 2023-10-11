package band.effective.headlines.compose.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import band.effective.headlines.compose.navigation.TopLevelDestination

@Composable
fun BottomNavigationItemIcon(item: TopLevelDestination, selected: Boolean) {
    val painter = when (item) {
        is TopLevelDestination.ResourceIcon -> painterResource(item.iconResId)
        is TopLevelDestination.ImageVectorIcon -> rememberVectorPainter(item.iconImageVector)
    }
    val selectedPainter = when (item) {
        is TopLevelDestination.ResourceIcon -> item.selectedIconResId?.let { painterResource(it) }
        is TopLevelDestination.ImageVectorIcon -> item.selectedImageVector?.let {
            rememberVectorPainter(it)
        }
    }

    if (selectedPainter != null) {
        Crossfade(targetState = selected, label = "bottom_nav_anim") {
            Icon(
                painter = if (it) selectedPainter else painter,
                contentDescription = stringResource(item.contentDescriptionResId),
            )
        }
    } else {
        Icon(
            painter = painter,
            contentDescription = stringResource(item.contentDescriptionResId),
        )
    }
}
