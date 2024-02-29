package com.example.domain

import kotlinx.coroutines.flow.Flow

interface GetGithubUsersUseCase {
    operator fun invoke(): Flow<List<String>>
}