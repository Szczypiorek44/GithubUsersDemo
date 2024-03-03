package com.example.data

import com.example.data.api.GithubApi
import com.example.data.api.STARTING_USER_ID
import com.example.data.api.downloadUsersAsResult
import com.example.data.database.dao.UserDao
import com.example.data.utils.asDomainModelList
import com.example.data.utils.toUserEntityList
import com.example.domain.models.FetchNextUsersResult
import com.example.domain.models.User
import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext

internal class GithubUsersRepositoryImpl(
    private val githubApi: GithubApi,
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher,
    scope: CoroutineScope,
) : GithubUsersRepository {

    private val usersFlow = userDao.observeEntities()
        .map { it.asDomainModelList() }
        .shareIn(scope, started = SharingStarted.Lazily, replay = 1)

    override fun observeUsers(): Flow<List<User>> = usersFlow

    override suspend fun fetchNextUsers(): FetchNextUsersResult = withContext(ioDispatcher) {
        val lastUserId = getLastUserId()
        downloadAndStoreUsers(lastUserId)
    }

    override suspend fun getUser(userId: Int): User? = withContext(ioDispatcher) {
        usersFlow.first()
            .find { it.id == userId }
    }

    private suspend fun downloadAndStoreUsers(sinceId: Int): FetchNextUsersResult {
        val downloadResult = githubApi.downloadUsersAsResult(sinceId)
            ?: return FetchNextUsersResult.Error

        val githubUsers = downloadResult.githubUsers
        val canFetchMoreUsers = downloadResult.canFetchMoreUsers

        userDao.insertOrIgnore(githubUsers.toUserEntityList())

        return FetchNextUsersResult.Success(canFetchMoreUsers)
    }

    private suspend fun getLastUserId(): Int {
        val lastEmittedUser = usersFlow.first()
            .lastOrNull()

        return lastEmittedUser?.id ?: STARTING_USER_ID
    }


}

