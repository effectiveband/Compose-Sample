package band.effective.headlines.compose.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TopLevelDestination(
    val route: String,
    @StringRes val labelResId: Int,
    @StringRes val contentDescriptionResId: Int,
) {
    open class ResourceIcon(
        screen: String,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null,
    ) : TopLevelDestination(screen, labelResId, contentDescriptionResId)

    open class ImageVectorIcon(
        route: String,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        val iconImageVector: ImageVector,
        val selectedImageVector: ImageVector? = null,
    ) : TopLevelDestination(route, labelResId, contentDescriptionResId)
}
