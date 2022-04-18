package band.effective.headlines.compose.feed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import band.effective.headlines.compose.core_ui.components.ImageWithPlaceholder
import band.effective.headlines.compose.feed.R
import band.effective.headlines.compose.feed.presentation.models.HeadlineItemUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HeadlineCard(headline: HeadlineItemUi, modifier: Modifier = Modifier) {
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        if (headline.imageUrl != null) {
            ImageWithPlaceholder(
                imageUrl = headline.imageUrl, modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            ExpandableHeadlineTitle(
                title = headline.title,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = headline.description ?: stringResource(id = R.string.no_description),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            HeadlineSourceWithDateChip(
                source = headline.source,
                date = headline.date,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }
    }
}
