package band.effective.headlines.compose.article_details.screen.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import band.effective.headlines.compose.article_details.screen.R

@Composable
internal fun BackButton(
    modifier: Modifier,
    onBack: () -> Unit
) {
    IconButton(
        onClick = { onBack() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back_nav_icon)
        )
    }
}
