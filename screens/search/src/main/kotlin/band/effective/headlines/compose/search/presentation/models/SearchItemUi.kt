package band.effective.headlines.compose.search.presentation.models

internal class SearchItemUi(
    val title: String,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val date: String,
    val description: String?,
    val content: String?
)

internal fun SearchItemUi.asNavArg(): SearchItemNavArg {
    return SearchItemNavArg(title, source, url, imageUrl, date, description, content)
}
