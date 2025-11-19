package com.maxidev.repostars.domain.model

data class GitHubRepo(
    val nodeId: String,
    val name: String,
    val ownerLogin: String,
    val ownerUrl: String,
    val ownerAvatar: String,
    val description: String,
    val repositoryUrl: String,
    val stargazersCount: Int,
    val language: String
)