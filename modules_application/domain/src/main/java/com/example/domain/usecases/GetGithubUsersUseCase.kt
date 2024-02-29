package com.example.domain.usecases

import com.example.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GetGithubUsersUseCase {
    operator fun invoke(): Flow<List<User>>
}