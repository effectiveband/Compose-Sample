package band.effective.headlines.compose.core.navigation

import android.net.Uri

const val ENCODED_NULL = "%@null@"
val DECODED_NULL: String = Uri.decode(ENCODED_NULL)

fun encodeForRoute(arg: String): String = Uri.encode(arg)
