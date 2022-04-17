package band.effective.headlines.compose.news_api.data

import android.content.Context
import android.os.Build
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
    context: Context,
    private val newsApiDataSource: NewsApiDataSource
) : NewsRepository {

    private val country = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0].country
    } else {
        context.resources.configuration.locale.country
    }

    override fun getHeadlinesPagedFlow(): Flow<PagingData<ArticleDomain>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = {
                NewsPagingSource { page, pageSize ->
                    newsApiDataSource.getHeadlines(country, page, pageSize)
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
