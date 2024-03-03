package com.example.data.api.models

data class DownloadUsersResult(
    val githubUsers: List<GithubUser>,
    val canFetchMoreUsers: Boolean
)