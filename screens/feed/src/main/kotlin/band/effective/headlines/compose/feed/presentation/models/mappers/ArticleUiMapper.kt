package band.effective.headlines.compose.feed.presentation.models.mappers

import android.text.format.DateUtils
import band.effective.headlines.compose.feed.presentation.models.HeadlineItemUi
import band.effective.headlines.compose.news_api.domain.models.ArticleDomain


internal fun ArticleDomain.asHeadlineItemUI(): HeadlineItemUi {
    val relativeDateString = DateUtils.getRelativeTimeSpanString(
        date.time,
        System.currentTimeMillis(),
        DateUtils.DAY_IN_MILLIS
    )
    return HeadlineItemUi(
        title = title,
        source = source,
        url = url,
        imageUrl = imageUrl,
        date = relativeDateString.toString()
    )
}
