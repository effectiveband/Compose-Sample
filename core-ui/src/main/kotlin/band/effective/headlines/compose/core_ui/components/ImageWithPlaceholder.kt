package band.effective.headlines.compose.core_ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest

@Composable
fun ImageWithPlaceholder(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    ) {
        when (painter.state) {
            AsyncImagePainter.State.Empty -> {
                FadePlaceholder(modifier = Modifier.fillMaxSize())
            }
            is AsyncImagePainter.State.Loading -> {
                FadePlaceholder(modifier = Modifier.fillMaxSize())
            }
            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            is AsyncImagePainter.State.Error -> {
                ErrorImage(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
