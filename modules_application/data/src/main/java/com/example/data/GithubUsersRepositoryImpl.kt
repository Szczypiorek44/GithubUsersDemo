package com.example.data

import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class GithubUsersRepositoryImpl : GithubUsersRepository {

    private val users = listOf(
        "user1", "user2", "user3", "user4",
        "user5", "user6", "user7", "user8",
        "user9", "user10", "user11", "user12",
    )

    override fun getUsers(): Flow<List<String>> {
        return flow {
            emit(users.subList(0, 3))
            delay(5000)
            emit(users.subList(0, 7))
            delay(5000)
            emit(users.subList(0, 11))
        }
    }
}