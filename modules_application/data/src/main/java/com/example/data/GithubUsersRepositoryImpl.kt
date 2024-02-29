package com.example.data

import com.example.data.api.GithubApi
import com.example.data.models.GithubUser
import com.example.data.models.mapToUserList
import com.example.domain.models.User
import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

internal class GithubUsersRepositoryImpl(
    private val githubApi: GithubApi,
    private val scope: CoroutineScope
) : GithubUsersRepository {

    private val users = listOf(
        User(1, "John"),
        User(2, "Arthur"),
        User(3, "Sylvia"),
        User(4, "Anna"),
        User(5, "Bob"),
        User(6, "Luke"),
        User(7, "Barbara"),
        User(8, "Tom"),
        User(9, "Jerry"),
        User(10, "Agata"),
        User(11, "Brittany"),
        User(12, "Austin"),
        User(13, "Blake"),
        User(14, "Magda"),
        User(15, "Caroline"),
        User(16, "Pavol"),
        User(17, "Michael"),
        User(18, "John"),
        User(19, "Bobby"),
    )

    override fun getUsers(): Flow<List<User>> = flow {
        downloadGithubUsers()
            .onSuccess {
                emit(it.mapToUserList())
            }
            .onFailure {
                emptyList<User>()
            }
    }.stateIn(scope, initialValue = emptyList(), started = SharingStarted.Lazily)

    private suspend fun downloadGithubUsers(): Result<List<GithubUser>> = runCatching {
        githubApi.getUsers()
    }
}

