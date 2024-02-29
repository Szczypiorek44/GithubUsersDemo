package com.example.data.models

import com.example.domain.models.User
import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
)

fun List<GithubUser>.mapToUserList(): List<User> = this.map {
    User(it.id, it.login)
}