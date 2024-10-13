package com.ingenious.githubapp.domain.usecase

import com.ingenious.githubapp.domain.model.GithubUserDetails
import com.ingenious.githubapp.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend fun run(username: String): Result<GithubUserDetails> =
        userRepository.getUserDetails(username)
}