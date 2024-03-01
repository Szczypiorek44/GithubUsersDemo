package com.example.domain.usecases

import com.example.domain.models.User
import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


internal class ObserveUsersUseCaseImpl @Inject constructor(
    private val githubUsersRepository: GithubUsersRepository
) : ObserveUsersUseCase {
    override fun invoke(): Flow<List<User>> {
        return githubUsersRepository.observeUsers()
    }
}