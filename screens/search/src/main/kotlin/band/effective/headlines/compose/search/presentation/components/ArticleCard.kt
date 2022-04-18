package band.effective.headlines.compose.search.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.headlines.compose.core_ui.components.ImageWithPlaceholder
import band.effective.headlines.compose.search.presentation.models.SearchItemUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArticleCard(article: SearchItemUi, modifier: Modifier = Modifier) {
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        if (article.imageUrl != null) {
            ImageWithPlaceholder(
                imageUrl = article.imageUrl, modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
        Text(
            text = article.title,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
