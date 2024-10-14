package com.ingenious.githubapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.ingenious.githubapp.data.model.persistance.UserDto
import com.ingenious.githubapp.domain.model.UserEntity
import com.ingenious.githubapp.domain.model.UserDetailsEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getAllUsers(): Flow<PagingData<UserEntity>>

    suspend fun getUserDetails(login: String) : Result<UserDetailsEntity>
}