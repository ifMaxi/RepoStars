package com.maxidev.repostars.ui.search.mapper

import com.maxidev.repostars.domain.model.GitHubRepo
import com.maxidev.repostars.ui.search.model.GitHubRepoUi

fun GitHubRepo.asUi(): GitHubRepoUi =
    GitHubRepoUi(
        nodeId = nodeId,
        name = name,
        ownerLogin = ownerLogin,
        ownerUrl = ownerUrl,
        ownerAvatar = ownerAvatar,
        description = description,
        repositoryUrl = repositoryUrl,
        stargazersCount = "$stargazersCount",
        language = language
    )