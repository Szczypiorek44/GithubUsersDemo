package com.example.domain

import kotlinx.coroutines.flow.Flow


class GetGithubUsersUseCaseImpl constructor(
    private val githubUsersRepository: GithubUsersRepository
) : GetGithubUsersUseCase {
    override fun invoke(): Flow<List<String>> {
        return githubUsersRepository.getUsers()
    }
}