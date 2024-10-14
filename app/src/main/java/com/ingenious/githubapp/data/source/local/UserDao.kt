package com.ingenious.githubapp.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ingenious.githubapp.data.model.persistance.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): PagingSource<Int, UserDto>

    @Query("SELECT id FROM user WHERE id = (SELECT MAX(id) FROM user)")
    suspend fun getLastUserId(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userDto: UserDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: UserDto)

    @Delete
    suspend fun delete(userDto: UserDto)
}