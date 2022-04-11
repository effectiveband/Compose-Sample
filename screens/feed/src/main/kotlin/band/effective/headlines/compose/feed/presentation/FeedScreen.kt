package band.effective.headlines.compose.feed.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import band.effective.headlines.compose.feed.presentation.FeedScreenNavigation
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun FeedScreen(navigator: FeedScreenNavigation) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Feed", modifier = Modifier)
        Button(onClick = navigator::openHeadlineDetails) {
            Text(text = "To Details")
        }
    }
}
