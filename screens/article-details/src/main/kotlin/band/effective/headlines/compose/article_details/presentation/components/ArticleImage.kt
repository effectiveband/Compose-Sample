package band.effective.headlines.compose.article_details.presentation.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.headlines.compose.core_ui.components.ImageWithPlaceholder

@Composable
internal fun ArticleImage(imageUrl: String, modifier: Modifier = Modifier) {
    ImageWithPlaceholder(
        imageUrl = imageUrl,
        modifier
            .heightIn(max = 250.dp)
            .clip(RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp))
    )
}
