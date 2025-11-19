package com.maxidev.repostars.data.mapper

import com.maxidev.repostars.data.remote.dto.GitHubRepositoriesDto
import com.maxidev.repostars.domain.model.GitHubRepo

fun GitHubRepositoriesDto.asDomain(): List<GitHubRepo> =
    this.items.map { result ->
        GitHubRepo(
            nodeId = result?.nodeId.orEmpty(),
            name = result?.name.orEmpty(),
            ownerLogin = result?.owner?.login.orEmpty(),
            ownerUrl = result?.owner?.htmlUrl.orEmpty(),
            ownerAvatar = result?.owner?.avatarUrl.orEmpty(),
            description = result?.description.orEmpty(),
            repositoryUrl = result?.htmlUrl.orEmpty(),
            stargazersCount = result?.stargazersCount ?: 0,
            language = result?.language.orEmpty()
        )
    }