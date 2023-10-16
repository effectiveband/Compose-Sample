package band.effective.headlines.compose.core.navigation

import androidx.navigation.NamedNavArgument

interface Destination {

    val baseRoute: String

    val screenRoute: String

    val arguments: List<NamedNavArgument>
}
