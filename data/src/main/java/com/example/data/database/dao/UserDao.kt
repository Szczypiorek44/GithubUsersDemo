package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.models.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query(value = "SELECT * FROM users ORDER BY id ASC")
    fun observeEntities(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(userEntities: List<UserEntity>)


}