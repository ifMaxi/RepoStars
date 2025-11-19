package com.maxidev.repostars.domain.repository

import androidx.paging.PagingData
import com.maxidev.repostars.domain.model.GitHubRepo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun fetchSearchedRepository(query: String): Flow<PagingData<GitHubRepo>>
}