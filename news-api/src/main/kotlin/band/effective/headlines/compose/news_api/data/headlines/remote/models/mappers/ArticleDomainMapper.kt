package band.effective.headlines.compose.news_api.data.headlines.remote.models.mappers

import band.effective.headlines.compose.news_api.data.headlines.remote.models.ArticleResponse
import band.effective.headlines.compose.news_api.domain.models.ArticleDomain

internal fun ArticleResponse.toArticle(): ArticleDomain {
    return ArticleDomain(
        title = title,
        description = description,
        source = source.name,
        url = url,
        imageUrl = imageUrl,
        date = date,
        content = content
    )
}
