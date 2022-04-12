package band.effective.headlines.compose.news_api.data.headlines.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
internal class NewsPageResponse(
    @Json(name = "totalResults")
    val total: Int,
    @Json(name = "articles")
    val articles: List<ArticleResponse>
)

@JsonClass(generateAdapter = true)
internal class ArticleResponse(
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "source")
    val source: ArticleSourceResponse,
    @Json(name = "url")
    val url: String,
    @Json(name = "urlToImage")
    val imageUrl: String?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "publishedAt")
    val date: Date
)

@JsonClass(generateAdapter = true)
class ArticleSourceResponse(
    @Json(name = "name")
    val name: String
)
