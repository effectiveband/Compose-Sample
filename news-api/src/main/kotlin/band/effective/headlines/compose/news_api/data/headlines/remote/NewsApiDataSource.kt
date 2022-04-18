package band.effective.headlines.compose.news_api.data.headlines.remote

import band.effective.headlines.compose.core.entity.Either
import band.effective.headlines.compose.core.entity.ErrorReason
import band.effective.headlines.compose.news_api.data.headlines.remote.models.NewsPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsApiDataSource {

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val START_PAGE = 1
    }

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("page") page: Int = START_PAGE,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE
    ): Either<ErrorReason, NewsPageResponse>

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = START_PAGE,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("sortBy") sortBy: String = "popularity",
    ): Either<ErrorReason, NewsPageResponse>
}
