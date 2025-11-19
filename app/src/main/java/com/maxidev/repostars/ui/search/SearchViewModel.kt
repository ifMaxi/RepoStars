package com.maxidev.repostars.ui.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.maxidev.repostars.domain.model.GitHubRepo
import com.maxidev.repostars.domain.usecase.SearchRepoUseCase
import com.maxidev.repostars.ui.search.mapper.asUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchRepoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    val textField = TextFieldState()

    fun searchResult(query: String) {
        _uiState.update { state ->
            state.copy(
                searchResult = searchUseCase(query)
                    .map { data -> data.map(GitHubRepo::asUi)}
                    .cachedIn(viewModelScope)
            )
        }
    }
}