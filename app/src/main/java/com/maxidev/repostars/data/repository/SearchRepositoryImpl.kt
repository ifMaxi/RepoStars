@file:OptIn(FlowPreview::class)

package com.maxidev.repostars.data.repository

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.maxidev.repostars.data.paging.SearchRepositoryPagingSource
import com.maxidev.repostars.data.remote.RepoStarsApiService
import com.maxidev.repostars.domain.model.GitHubRepo
import com.maxidev.repostars.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: RepoStarsApiService
) : SearchRepository {

    override fun fetchSearchedRepository(query: String): Flow<PagingData<GitHubRepo>> {
        val sourceFactory = {
            SearchRepositoryPagingSource(
                apiService = apiService,
                query = query
            )
        }
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = sourceFactory
        ).flow
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }
}