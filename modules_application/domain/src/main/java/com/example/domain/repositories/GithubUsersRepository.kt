package com.example.domain.repositories

import kotlinx.coroutines.flow.Flow

interface GithubUsersRepository {
    fun getUsers(): Flow<List<String>>
}