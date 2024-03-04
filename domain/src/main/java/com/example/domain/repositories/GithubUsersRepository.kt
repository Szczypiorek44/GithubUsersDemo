package com.example.domain.repositories

import com.example.domain.models.FetchNextUsersResult
import com.example.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GithubUsersRepository {

    fun observeUsers(): Flow<List<User>>

    suspend fun fetchNextUsers(): FetchNextUsersResult

    suspend fun getUser(userId: Int): User?
}