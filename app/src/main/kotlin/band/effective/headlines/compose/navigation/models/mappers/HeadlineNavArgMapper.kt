package band.effective.headlines.compose.navigation.models.mappers

import band.effective.headlines.compose.article_details.presentation.Article
import band.effective.headlines.compose.feed.presentation.models.HeadlineNavArg

fun HeadlineNavArg.asArticle() : Article {
    return Article(
        title = title,
        source = source,
        url = url,
        imageUrl= imageUrl,
        date = date,
        description = description,
        content = content
    )
}
