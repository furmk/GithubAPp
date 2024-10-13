package com.ingenious.githubapp.domain.usecase

import com.ingenious.githubapp.domain.model.GithubUser
import com.ingenious.githubapp.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend fun run(): Result<List<GithubUser>> =
        userRepository.getAllUsers()
}