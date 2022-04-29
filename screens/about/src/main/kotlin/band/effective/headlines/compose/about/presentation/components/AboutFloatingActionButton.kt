package band.effective.headlines.compose.about.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import band.effective.headlines.compose.about.R

@Composable
internal fun AboutFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Outlined.OpenInBrowser,
            contentDescription = stringResource(id = R.string.open_api_icon)
        )
    }
}
