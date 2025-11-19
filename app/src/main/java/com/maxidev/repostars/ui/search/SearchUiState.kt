package com.maxidev.repostars.ui.search

import androidx.paging.PagingData
import com.maxidev.repostars.ui.search.model.GitHubRepoUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val searchResult: Flow<PagingData<GitHubRepoUi>> = emptyFlow()
)