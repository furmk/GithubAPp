package com.ingenious.githubapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ingenious.githubapp.data.model.persistance.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<UserDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userDto: UserDto)

    @Delete
    suspend fun delete(userDto: UserDto)
}