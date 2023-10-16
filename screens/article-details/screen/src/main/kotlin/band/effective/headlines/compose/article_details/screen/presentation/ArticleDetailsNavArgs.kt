package band.effective.headlines.compose.article_details.screen.presentation

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import band.effective.headlines.compose.article_details.shared.ArticleNavArg
import band.effective.headlines.compose.core.navigation.DestinationNavType
import band.effective.headlines.compose.core.navigation.ENCODED_NULL
import band.effective.headlines.compose.core.navigation.ParcelableNavTypeSerializer
import band.effective.headlines.compose.core.navigation.encodeForRoute

internal val articleNavType = ArticleNavType(ParcelableNavTypeSerializer(ArticleNavArg::class.java))

internal class ArticleNavType(
    private val stringSerializer: ParcelableNavTypeSerializer
) : DestinationNavType<ArticleNavArg?>() {
    override fun serializeValue(value: ArticleNavArg?): String {
        return if (value == null) {
            ENCODED_NULL
        } else {
            encodeForRoute(stringSerializer.toRouteString(value))
        }
    }

    override fun get(savedStateHandle: SavedStateHandle, key: String): ArticleNavArg? {
        return savedStateHandle[key]
    }

    @Suppress("DEPRECATION")
    override fun get(bundle: Bundle, key: String): ArticleNavArg? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, ArticleNavArg::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): ArticleNavArg {
        return stringSerializer.fromRouteString(value) as ArticleNavArg
    }

    override fun put(bundle: Bundle, key: String, value: ArticleNavArg?) {
        bundle.putParcelable(key, value)
    }
}
