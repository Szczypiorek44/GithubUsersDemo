package com.example.githubusers.mocks

import com.example.domain.models.FetchNextUsersResult
import com.example.domain.usecases.FetchNextUsersUseCase

class FakeFetchNextUsersUseCase: FetchNextUsersUseCase {

    override suspend fun invoke(): FetchNextUsersResult {
        TODO("Not yet implemented")
    }
}