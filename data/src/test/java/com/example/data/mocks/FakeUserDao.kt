package com.example.data.mocks

import com.example.data.database.dao.UserDao
import com.example.data.database.models.UserEntity
import com.example.data.utils.toUserEntityList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FakeUserDao : UserDao {

    private val mutableUserEntities = MutableSharedFlow<List<UserEntity>>()

    suspend fun emit(value: List<UserEntity>) = mutableUserEntities.emit(value)

    override fun observeEntities(): Flow<List<UserEntity>> {
        return mutableUserEntities.asSharedFlow()
    }

    override suspend fun getAll(): List<UserEntity> {
        return fakeGithubUsers.toUserEntityList()
    }

    override suspend fun insertOrIgnore(userEntities: List<UserEntity>) {
    }
}