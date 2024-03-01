package com.example.domain.usecases

import com.example.domain.models.FetchNextUsersResult

interface FetchNextUsersUseCase {
    suspend operator fun invoke(): FetchNextUsersResult
}