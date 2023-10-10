package band.effective.headlines.compose.article_details.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ArticleContent(
    imageUrl: String?,
    title: String,
    description: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (imageUrl != null) ArticleImage(imageUrl = imageUrl, modifier = Modifier.fillMaxWidth())
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(
                modifier = Modifier
                    .height(76.dp)
                    .navigationBarsPadding()
            )
        }
    }
}
