package com.maxidev.repostars.ui.search.model

data class GitHubRepoUi(
    val nodeId: String,
    val name: String,
    val ownerLogin: String,
    val ownerUrl: String,
    val ownerAvatar: String,
    val description: String,
    val repositoryUrl: String,
    val stargazersCount: String,
    val language: String
)