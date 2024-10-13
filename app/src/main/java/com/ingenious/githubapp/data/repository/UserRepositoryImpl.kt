package com.ingenious.githubapp.data.repository

import com.ingenious.githubapp.data.mapper.GithubUserDetailsMapper
import com.ingenious.githubapp.data.mapper.GithubUserMapper
import com.ingenious.githubapp.data.service.GithubService
import com.ingenious.githubapp.domain.model.GithubUser
import com.ingenious.githubapp.domain.model.GithubUserDetails
import com.ingenious.githubapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Result.Companion.success

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val githubUserMapper: GithubUserMapper,
    private val githubUserDetailsMapper: GithubUserDetailsMapper,
) : UserRepository {

    override suspend fun getAllUsers(): Result<List<GithubUser>> = withContext(Dispatchers.IO) {
        githubService.getUsers()
            .map(githubUserMapper::from)
            .let(::success)
    }

    override suspend fun getUserDetails(username: String): Result<GithubUserDetails> =
        withContext(Dispatchers.IO) {
            githubService.getUser(username)
                .let(githubUserDetailsMapper::from)
                .let(::success)
        }
}