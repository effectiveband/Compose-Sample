package band.effective.headlines.compose.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class NewsErrorNetwork(
    @Json(name = "status")
    val status: String,
    @Json(name = "code")
    val code: String,
    @Json(name = "message")
    val message: String
)
