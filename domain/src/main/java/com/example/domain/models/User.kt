package com.example.domain.models

data class User(
    val id: Int,
    val name: String,
    val avatarUrl: String? = null,
    val countryName: String = "",
    val stateName: String = ""
)