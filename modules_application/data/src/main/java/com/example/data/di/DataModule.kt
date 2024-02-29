package com.example.data.di

import com.example.data.GithubUsersRepositoryImpl
import com.example.domain.repositories.GithubUsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGithubUsersRepository(): GithubUsersRepository = GithubUsersRepositoryImpl()

}