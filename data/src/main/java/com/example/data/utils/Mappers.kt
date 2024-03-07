package com.example.data.utils

import com.example.data.api.containsLinkNextHeader
import com.example.data.api.models.DownloadUsersResult
import com.example.data.api.models.GithubUser
import com.example.data.database.models.UserEntity
import com.example.data.utils.RandomCountryAndStateGenerator.CountryAndState
import com.example.domain.models.User
import retrofit2.Response

fun List<UserEntity>.asDomainModelList(): List<User> = map { it.asDomainModel() }

fun UserEntity.asDomainModel() = User(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    countryName = countryName,
    stateName = stateName
)


fun List<GithubUser>.toUserEntityList(countryAndState: CountryAndState): List<UserEntity> =
    this.map {
        UserEntity(
            id = it.id,
            name = it.login,
            avatarUrl = it.avatarUrl,
            countryName = countryAndState.countryName,
            stateName = countryAndState.stateName
        )
    }

fun Response<List<GithubUser>>.asDownloadUsersResult(): DownloadUsersResult {
    val githubUsers = body() ?: emptyList()
    val canFetchMoreUsers = containsLinkNextHeader()

    return DownloadUsersResult(githubUsers, canFetchMoreUsers)
}