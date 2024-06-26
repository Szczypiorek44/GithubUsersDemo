package com.example.domain.di

import com.example.domain.repositories.GithubUsersRepository
import com.example.domain.usecases.FetchNextUsersUseCase
import com.example.domain.usecases.FetchNextUsersUseCaseImpl
import com.example.domain.usecases.GetFilteredUsersUseCase
import com.example.domain.usecases.GetFilteredUsersUseCaseImpl
import com.example.domain.usecases.GetUserUseCase
import com.example.domain.usecases.GetUserUseCaseImpl
import com.example.domain.usecases.ObserveUsersUseCase
import com.example.domain.usecases.ObserveUsersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideFetchNextUsersUseCase(
        githubUsersRepository: GithubUsersRepository
    ): FetchNextUsersUseCase =
        FetchNextUsersUseCaseImpl(githubUsersRepository)

    @Provides
    fun provideGetFilteredUsersUseCase(
        githubUsersRepository: GithubUsersRepository
    ): GetFilteredUsersUseCase =
        GetFilteredUsersUseCaseImpl(githubUsersRepository)

    @Provides
    fun provideGetUserUseCase(
        githubUsersRepository: GithubUsersRepository
    ): GetUserUseCase =
        GetUserUseCaseImpl(githubUsersRepository)

    @Provides
    fun provideObserveUsersUseCase(
        githubUsersRepository: GithubUsersRepository
    ): ObserveUsersUseCase =
        ObserveUsersUseCaseImpl(githubUsersRepository)
}