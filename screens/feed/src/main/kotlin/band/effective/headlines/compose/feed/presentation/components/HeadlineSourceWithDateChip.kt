package band.effective.headlines.compose.feed.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import band.effective.headlines.compose.feed.R

@Composable
internal fun HeadlineSourceWithDateChip(
    source: String,
    date: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = source, style = MaterialTheme.typography.bodyMedium)
            Icon(
                imageVector = Icons.Default.Adjust,
                contentDescription = stringResource(id = R.string.delimiter_icon),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(16.dp)
            )
            Text(text = date, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
