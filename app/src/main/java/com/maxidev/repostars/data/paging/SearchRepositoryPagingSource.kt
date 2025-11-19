package com.maxidev.repostars.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maxidev.repostars.data.mapper.asDomain
import com.maxidev.repostars.data.remote.RepoStarsApiService
import com.maxidev.repostars.domain.model.GitHubRepo
import com.maxidev.repostars.utils.ConstantUtils.PAGE
import retrofit2.HttpException
import java.io.IOException

class SearchRepositoryPagingSource(
    private val apiService: RepoStarsApiService,
    private val query: String
) : PagingSource<Int, GitHubRepo>() {

    override fun getRefreshKey(state: PagingState<Int, GitHubRepo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubRepo> {
        return try {
            val page = params.key ?: PAGE
            val perPage = params.loadSize
            val response = apiService.getSearchRepositories(
                query = query,
                page = page,
                perPage = perPage
            )
            val mappingResponse = response.asDomain()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (mappingResponse.isEmpty()) null else page + 1

            LoadResult.Page(
                data = mappingResponse,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}