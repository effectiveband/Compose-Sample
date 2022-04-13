package band.effective.headlines.compose.news_api.data.headlines.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import band.effective.headlines.compose.core.entity.unpack
import band.effective.headlines.compose.news_api.data.headlines.remote.NewsApiDataSource.Companion.DEFAULT_PAGE_SIZE
import band.effective.headlines.compose.news_api.data.headlines.remote.NewsApiDataSource.Companion.START_PAGE
import band.effective.headlines.compose.news_api.data.headlines.remote.models.ArticleResponse
import band.effective.headlines.compose.news_api.data.headlines.remote.models.NewsLoadException
import band.effective.headlines.compose.news_api.data.headlines.remote.models.mappers.asDomain
import band.effective.headlines.compose.news_api.domain.models.ArticleDomain
import javax.inject.Inject

internal class NewsPagingSource @Inject constructor(
    private val newsApiDataSource: NewsApiDataSource
): PagingSource<Int, ArticleDomain>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDomain> {
        val page = params.key ?: START_PAGE
        val response = newsApiDataSource.getHeadlines("us", page)
        return response.unpack(
            success = { news ->
                LoadResult.Page(
                    data = news.articles.map(ArticleResponse::asDomain),
                    prevKey = (page - 1).takeIf { it >= 1 },
                    nextKey = (page + 1).takeIf { news.articles.size == DEFAULT_PAGE_SIZE },
                )
            },
            error = { reason ->
                LoadResult.Error(NewsLoadException(reason))
            }
        )
    }
}
