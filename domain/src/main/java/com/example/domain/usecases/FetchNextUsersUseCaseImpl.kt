package com.example.domain.usecases

import com.example.domain.models.FetchNextUsersResult
import com.example.domain.repositories.GithubUsersRepository
import javax.inject.Inject


internal class FetchNextUsersUseCaseImpl @Inject constructor(
    private val githubUsersRepository: GithubUsersRepository
) : FetchNextUsersUseCase {

    override suspend fun invoke(): FetchNextUsersResult {
        return githubUsersRepository.fetchNextUsers()
    }
}