package com.ingenious.githubapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ingenious.githubapp.data.model.persistance.UserDetailsDto

@Dao
interface UserDetailsDao {

    @Query("SELECT * FROM userDetails WHERE name = :name")
    suspend fun getUserDetails(name: String): UserDetailsDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetailsDto: UserDetailsDto)
}