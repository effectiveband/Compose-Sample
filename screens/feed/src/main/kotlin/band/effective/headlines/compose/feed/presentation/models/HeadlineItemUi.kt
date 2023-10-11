package band.effective.headlines.compose.feed.presentation.models

import band.effective.headlines.compose.article_details.shared.ArticleNavArg

internal class HeadlineItemUi(
    val title: String,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val date: String,
    val description: String?,
    val content: String?
)

internal fun HeadlineItemUi.asNavArg(): ArticleNavArg {
    return ArticleNavArg(title, source, url, imageUrl, date, description, content)
}
