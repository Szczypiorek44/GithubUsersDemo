package com.example.domain

import kotlinx.coroutines.flow.Flow

interface GithubUsersRepository {
    fun getUsers(): Flow<List<String>>
}