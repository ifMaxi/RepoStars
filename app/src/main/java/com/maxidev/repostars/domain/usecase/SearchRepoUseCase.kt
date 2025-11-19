package com.maxidev.repostars.domain.usecase

import androidx.paging.PagingData
import com.maxidev.repostars.domain.model.GitHubRepo
import com.maxidev.repostars.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepoUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<GitHubRepo>> =
        repository.fetchSearchedRepository(query)
}