package com.example.data.utils

import com.example.data.api.models.GithubUser
import com.example.data.database.models.UserEntity
import com.example.domain.models.User

fun List<UserEntity>.asExternalModelList(): List<User> = map { it.asExternalModel() }
fun UserEntity.asExternalModel() = User(
    id = id,
    name = name,
    avatarUrl = avatarUrl
)

fun List<GithubUser>.toUserEntityList(): List<UserEntity> = this.map {
    UserEntity(it.id, it.login, it.avatarUrl)
}