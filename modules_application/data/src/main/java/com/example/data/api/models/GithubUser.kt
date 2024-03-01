package com.example.data.api.models

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
)