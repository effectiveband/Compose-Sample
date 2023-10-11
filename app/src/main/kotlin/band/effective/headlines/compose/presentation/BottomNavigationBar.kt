package band.effective.headlines.compose.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import band.effective.headlines.compose.core_ui.surfaceColorAtElevation
import band.effective.headlines.compose.navigation.TopLevelDestination
import band.effective.headlines.compose.ui.theme.NoRippleTheme

@Composable
fun BottomNavigationBar(
    destinations: List<TopLevelDestination>,
    selectedItem: TopLevelDestination,
    onNavigationSelected: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Column {
            NavigationBar(modifier = modifier) {
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = selectedItem.route == destination.route,
                        onClick = {
                            onNavigationSelected(destination)
                        },
                        icon = {
                            BottomNavigationItemIcon(
                                item = destination,
                                selected = selectedItem.route == destination.route
                            )
                        },
                        label = {
                            Text(text = stringResource(id = destination.labelResId))
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            )
        }
    }
}
