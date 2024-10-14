package com.ingenious.githubapp.domain.usecase

import androidx.paging.PagingData
import com.ingenious.githubapp.domain.model.UserEntity
import com.ingenious.githubapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend fun run(): Flow<PagingData<UserEntity>> =
        userRepository.getAllUsers()
}