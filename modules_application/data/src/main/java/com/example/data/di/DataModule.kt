package com.example.data.di

import com.example.data.GithubUsersRepositoryImpl
import com.example.data.api.GithubApi
import com.example.data.database.dao.UserDao
import com.example.domain.repositories.GithubUsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGithubUsersRepository(
        githubApi: GithubApi,
        userDao: UserDao,
        @ApplicationScope scope: CoroutineScope,
        @Dispatcher(DispatcherType.IO) ioDispatcher: CoroutineDispatcher,
    ): GithubUsersRepository =
        GithubUsersRepositoryImpl(githubApi, userDao, ioDispatcher, scope)

}