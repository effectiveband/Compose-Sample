package band.effective.headlines.compose.search.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import band.effective.headlines.compose.search.R

@Composable
internal fun SearchField(text: String, modifier: Modifier = Modifier, onType: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = { onType(it) },
        modifier = modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(28.dp)),
        placeholder = { Text(text = stringResource(id = R.string.search)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(id = R.string.search_icon)
            )
        },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
