package com.example.data

import com.example.domain.models.User
import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class GithubUsersRepositoryImpl : GithubUsersRepository {

    private val users = listOf(
        User("1", "John"),
        User("2", "Arthur"),
        User("3", "Sylvia"),
        User("4", "Anna"),
        User("5", "Bob"),
        User("6", "Luke"),
        User("7", "Barbara"),
        User("8", "Tom"),
        User("9", "Jerry"),
        User("10", "Agata"),
        User("11", "Brittany"),
        User("12", "Austin"),
    )

    override fun getUsers(): Flow<List<User>> {
        return flow {
            delay(5000)
            emit(users.subList(0, 3))
            delay(5000)
            emit(users.subList(0, 7))
            delay(5000)
            emit(users.subList(0, 11))
        }
    }
}