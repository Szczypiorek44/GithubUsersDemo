package com.example.domain.usecases

import com.example.domain.models.User

interface GetUserUseCase {
    suspend operator fun invoke(userId: Int): User?
}