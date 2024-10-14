package com.ingenious.githubapp.data.model.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDto(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "login") val login: String,
)
