package com.example.features.list.mocks

import com.example.domain.models.User
import com.example.domain.usecases.ObserveUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FakeObserveUsersUseCase : ObserveUsersUseCase {

    private val mutableUserList = MutableSharedFlow<List<User>>()

    suspend fun emit(value: List<User>) = mutableUserList.emit(value)

    override fun invoke(): Flow<List<User>> = mutableUserList.asSharedFlow()
}