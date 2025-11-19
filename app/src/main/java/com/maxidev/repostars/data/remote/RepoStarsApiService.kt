package com.maxidev.repostars.data.remote

import com.maxidev.repostars.data.remote.dto.GitHubRepositoriesDto
import com.maxidev.repostars.utils.ConstantUtils.REPOSITORIES
import com.maxidev.repostars.utils.ConstantUtils.SEARCH
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoStarsApiService {

    @GET(SEARCH + REPOSITORIES)
    suspend fun getSearchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String? = "stars",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): GitHubRepositoriesDto
}