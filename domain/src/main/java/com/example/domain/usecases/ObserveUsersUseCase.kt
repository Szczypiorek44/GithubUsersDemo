package com.example.domain.usecases

import com.example.domain.models.User
import kotlinx.coroutines.flow.Flow

interface ObserveUsersUseCase {
    operator fun invoke(): Flow<List<User>>
}