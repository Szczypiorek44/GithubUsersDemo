package com.example.domain.di

import com.example.domain.repositories.GithubUsersRepository
import com.example.domain.usecases.GetGithubUsersUseCase
import com.example.domain.usecases.GetGithubUsersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetGithubUsersUseCase(
        githubUsersRepository: GithubUsersRepository
    ): GetGithubUsersUseCase =
        GetGithubUsersUseCaseImpl(githubUsersRepository)
}