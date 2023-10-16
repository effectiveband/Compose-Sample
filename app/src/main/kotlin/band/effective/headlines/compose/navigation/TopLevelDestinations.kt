package band.effective.headlines.compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Feed
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import band.effective.headlines.compose.R

object HeadlinesTopLevelDestination : TopLevelDestination.ImageVectorIcon(
    route = "headlines_graph",
    labelResId = R.string.headlines,
    contentDescriptionResId = R.string.headlines,
    iconImageVector = Icons.Outlined.Feed,
    selectedImageVector = Icons.Default.Feed
)

object SearchTopLevelDestination : TopLevelDestination.ImageVectorIcon(
    route = "search_graph",
    labelResId = R.string.search,
    contentDescriptionResId = R.string.search,
    iconImageVector = Icons.Outlined.Search,
    selectedImageVector = Icons.Default.Search
)

object AboutTopLevelDestination : TopLevelDestination.ImageVectorIcon(
    route = "about_graph",
    labelResId = R.string.about,
    contentDescriptionResId = R.string.about,
    iconImageVector = Icons.Outlined.Info,
    selectedImageVector = Icons.Default.Info
)
