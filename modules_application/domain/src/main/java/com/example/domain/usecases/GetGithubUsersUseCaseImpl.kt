package com.example.domain.usecases

import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


internal class GetGithubUsersUseCaseImpl @Inject constructor(
    private val githubUsersRepository: GithubUsersRepository
) : GetGithubUsersUseCase {
    override fun invoke(): Flow<List<String>> {
        return githubUsersRepository.getUsers()
    }
}