package band.effective.headlines.compose.feed.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
internal fun ExpandableHeadlineTitle(
    title: String,
    modifier: Modifier = Modifier,
    expandedContent: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val iconRotationState by animateFloatAsState(targetValue = if (isExpanded) 180F else 0F)

    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(7F)
            )
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .rotate(iconRotationState)
                    .weight(1F)
            ) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
        AnimatedVisibility(visible = isExpanded) {
            expandedContent()
        }
    }
}
