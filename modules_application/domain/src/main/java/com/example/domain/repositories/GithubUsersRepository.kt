package com.example.domain.repositories

import com.example.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GithubUsersRepository {
    fun getUsers(): Flow<List<User>>
}