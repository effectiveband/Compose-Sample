package band.effective.headlines.compose.search.presentation.models.mappers

import android.text.format.DateUtils
import band.effective.headlines.compose.news_api.domain.models.ArticleDomain
import band.effective.headlines.compose.search.presentation.models.SearchItemUi

internal fun ArticleDomain.asSearchItemUI(): SearchItemUi {
    val relativeDateString = DateUtils.getRelativeTimeSpanString(
        date.time,
        System.currentTimeMillis(),
        DateUtils.DAY_IN_MILLIS
    )
    return SearchItemUi(
        title = title,
        source = source,
        url = url,
        imageUrl = imageUrl,
        date = relativeDateString.toString()
    )
}
