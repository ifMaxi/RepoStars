package com.maxidev.repostars.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoriesDto(val items: List<Item?> = listOf()) {
    @Serializable
    data class Item(
        @SerialName("node_id")
        val nodeId: String? = "",
        val name: String? = "",
        val owner: Owner? = Owner(),
        @SerialName("html_url")
        val htmlUrl: String? = "",
        val description: String? = "",
        @SerialName("stargazers_count")
        val stargazersCount: Int? = 0,
        val language: String? = ""
    ) {
        @Serializable
        data class Owner(
            val login: String? = "",
            @SerialName("html_url")
            val htmlUrl: String? = "",
            @SerialName("avatar_url")
            val avatarUrl: String? = ""
        )
    }
}