package com.example.domain.usecases

import kotlinx.coroutines.flow.Flow

interface GetGithubUsersUseCase {
    operator fun invoke(): Flow<List<String>>
}