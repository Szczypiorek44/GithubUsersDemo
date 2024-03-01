package com.example.domain.di

import com.example.domain.repositories.GithubUsersRepository
import com.example.domain.usecases.FetchNextUsersUseCase
import com.example.domain.usecases.FetchNextUsersUseCaseImpl
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
    fun provideObserveUsersUseCase(
        githubUsersRepository: GithubUsersRepository
    ): ObserveUsersUseCase =
        ObserveUsersUseCaseImpl(githubUsersRepository)

    @Provides
    fun provideFetchNextUsersUseCase(
        githubUsersRepository: GithubUsersRepository
    ): FetchNextUsersUseCase =
        FetchNextUsersUseCaseImpl(githubUsersRepository)
}