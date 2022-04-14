package band.effective.headlines.compose.news_api.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import band.effective.headlines.compose.news_api.data.headlines.remote.NewsApiDataSource
import band.effective.headlines.compose.news_api.data.headlines.remote.NewsApiDataSource.Companion.DEFAULT_PAGE_SIZE
import band.effective.headlines.compose.news_api.data.headlines.remote.NewsPagingSource
import band.effective.headlines.compose.news_api.domain.models.ArticleDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class NewsRepositoryImpl @Inject constructor(
    private val newsApiDataSource: NewsApiDataSource
) : NewsRepository {

    override fun getHeadlinesPagedFlow(): Flow<PagingData<ArticleDomain>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = {
                NewsPagingSource { page, pageSize ->
                    newsApiDataSource.getHeadlines("us", page, pageSize)
                }
            }
        ).flow
    }

    override fun getEverythingPagedFlow(searchQuery: String): Flow<PagingData<ArticleDomain>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = {
                NewsPagingSource { page, pageSize ->
                    newsApiDataSource.getEverything(searchQuery, page, pageSize)
                }
            }
        ).flow
    }
}
