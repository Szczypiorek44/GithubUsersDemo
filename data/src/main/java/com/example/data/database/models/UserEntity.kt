package com.example.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val countryName: String,
    val stateName: String
)