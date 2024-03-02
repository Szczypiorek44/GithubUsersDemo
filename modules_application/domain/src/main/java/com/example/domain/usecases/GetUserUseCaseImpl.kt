package com.example.domain.usecases

import com.example.domain.repositories.GithubUsersRepository
import javax.inject.Inject

internal class GetUserUseCaseImpl @Inject constructor(
    private val githubUsersRepository: GithubUsersRepository
) : GetUserUseCase {
    override suspend fun invoke(userId: Int) = githubUsersRepository.getUser(userId)
}