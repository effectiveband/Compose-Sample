package band.effective.headlines.compose.news_api.data.headlines.remote.models.mappers

import band.effective.headlines.compose.news_api.data.headlines.remote.models.ArticleResponse
import band.effective.headlines.compose.news_api.domain.models.ArticleDomain

internal fun ArticleResponse.asDomain(): ArticleDomain {
    return ArticleDomain(
        title = title,
        description = description.orEmpty(),
        source = source.name,
        url = url,
        imageUrl = imageUrl,
        date = date,
        content = content.orEmpty()
    )
}
