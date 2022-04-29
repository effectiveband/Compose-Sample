package band.effective.headlines.compose.navigation.models.mappers

import band.effective.headlines.compose.article_details.presentation.Article
import band.effective.headlines.compose.search.presentation.models.SearchItemNavArg

fun SearchItemNavArg.asArticle(): Article {
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
