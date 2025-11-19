@file:OptIn(ExperimentalCoilApi::class)

package com.maxidev.repostars.ui.search

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.maxidev.repostars.ui.components.CustomSearchBar
import com.maxidev.repostars.ui.search.model.GitHubRepoUi
import com.maxidev.repostars.ui.theme.backgroundGradientDark
import com.maxidev.repostars.ui.theme.backgroundGradientLight
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import retrofit2.HttpException
import java.io.IOException

@Serializable data object SearchView

fun NavGraphBuilder.searchDestination() {
    composable<SearchView> {
        val viewModel = hiltViewModel<SearchViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val queryField = viewModel.textField

        ScreenContent(
            uiState = state,
            onSearch = viewModel::searchResult,
            textFieldState = queryField
        )
    }
}

@Composable
private fun ScreenContent(
    uiState: SearchUiState,
    textFieldState: TextFieldState = rememberTextFieldState(),
    onSearch: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val result = uiState.searchResult.collectAsLazyPagingItems()
    val lazyState = rememberLazyListState()
    val backgroundMode =
        if (isSystemInDarkTheme()) backgroundGradientDark else backgroundGradientLight

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            CustomSearchBar(
                modifier = Modifier.systemBarsPadding(),
                onSearch = onSearch,
                textFieldState = textFieldState
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .background(backgroundMode),
            state = lazyState,
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = result.itemCount,
                key = result.itemKey { key -> key.nodeId }
            ) { index ->
                val content = result[index]

                if (content != null) {
                    SearchRepoItem(model = content)
                }
            }

            result.loadState.let { states ->
                when {
                    states.refresh is LoadState.NotLoading && result.itemCount < 1 -> {
                        scope.launch {
                            snackBarHostState
                                .showSnackbar(
                                    message = "No data available.",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Indefinite
                                )
                        }
                    }
                    states.refresh is LoadState.Error -> {
                        val loadRefresh = states.refresh as LoadState.Error
                        val message = when (loadRefresh.error) {
                            is HttpException -> "Something went wrong."
                            is IOException -> "No internet connection."
                            else -> "Unknown error."
                        }

                        scope.launch {
                            snackBarHostState
                                .showSnackbar(
                                    message = message,
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Indefinite
                                )
                        }
                    }
                    states.append is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }
                    states.append is LoadState.Error -> {
                        scope.launch {
                            snackBarHostState
                                .showSnackbar(
                                    message = "Something went wrong.",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Indefinite
                                )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchRepoItem(
    modifier: Modifier = Modifier,
    model: GitHubRepoUi
) {
    val context = LocalContext.current
    val actionViewIntent = Intent.ACTION_VIEW
    val profileViewIntent = Intent(actionViewIntent, model.ownerUrl.toUri())
    val repositoryViewIntent = Intent(actionViewIntent, model.repositoryUrl.toUri())

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = model.ownerAvatar,
                contentDescription = model.ownerLogin,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable { context.startActivity(profileViewIntent) }
            )
            Spacer(Modifier.padding(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "${model.ownerLogin}/",
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = model.name,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = Color(0xFF0969DA),
                    modifier = Modifier
                        .clickable { context.startActivity(repositoryViewIntent) }
                )
            }
        }

        if (model.description.isNotEmpty()) {
            Text(
                text = model.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "⭐ ${model.stargazersCount}",
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(Modifier.padding(8.dp))

            languageColors.forEach { (name, hex) ->
                if (model.language == name) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .background(hex)
                    )
                }
            }

            Text(
                text = model.language,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
private fun SearchRepoItemPreview() {
    val previewHandler = AsyncImagePreviewHandler { ColorImage(Color.Black.toArgb()) }
    val model = GitHubRepoUi(
        nodeId = "",
        name = "very_very_very_large_project_name",
        ownerLogin = "loremImpsum",
        ownerUrl = "",
        ownerAvatar = "",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem impsum.",
        repositoryUrl = "",
        stargazersCount = "2560",
        language = "Kotlin"
    )

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        SearchRepoItem(model = model)
    }
}