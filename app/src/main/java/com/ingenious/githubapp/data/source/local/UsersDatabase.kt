package com.ingenious.githubapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ingenious.githubapp.data.model.persistance.UserDetailsDto
import com.ingenious.githubapp.data.model.persistance.UserDto

@Database(
    entities = [UserDto::class, UserDetailsDto::class],
    version = 1
)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun userDetailsDao(): UserDetailsDao
}