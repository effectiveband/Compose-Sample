package band.effective.headlines.compose.news_api.domain.models

import java.util.Date

class ArticleDomain(
    val title: String,
    val description: String,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val date: Date,
    val content: String
)
