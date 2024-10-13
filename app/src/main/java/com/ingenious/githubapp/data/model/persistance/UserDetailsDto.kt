package com.ingenious.githubapp.data.model.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userDetails")
data class UserDetailsDto(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "avatarUrl") val avatarUrl: String,
)
