package band.effective.headlines.compose.article_details.shared

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ArticleNavArg(
    val title: String,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val date: String,
    val description: String?,
    val content: String?
) : Parcelable
