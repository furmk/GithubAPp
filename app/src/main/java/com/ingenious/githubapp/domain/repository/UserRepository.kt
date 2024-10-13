package com.ingenious.githubapp.domain.repository

import com.ingenious.githubapp.domain.model.UserEntity
import com.ingenious.githubapp.domain.model.UserDetailsEntity

interface UserRepository {

    suspend fun getAllUsers(): Result<List<UserEntity>>

    suspend fun getUserDetails(username: String) : Result<UserDetailsEntity>
}