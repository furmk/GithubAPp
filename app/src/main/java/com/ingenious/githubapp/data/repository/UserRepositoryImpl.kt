package com.ingenious.githubapp.data.repository

import com.ingenious.githubapp.data.mapper.UserDetailsEntityMapper
import com.ingenious.githubapp.data.mapper.UserMapper
import com.ingenious.githubapp.data.source.remote.GithubService
import com.ingenious.githubapp.domain.model.UserEntity
import com.ingenious.githubapp.domain.model.UserDetailsEntity
import com.ingenious.githubapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Result.Companion.success

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val userMapper: UserMapper,
    private val userDetailsEntityMapper: UserDetailsEntityMapper,
) : UserRepository {

    override suspend fun getAllUsers(): Result<List<UserEntity>> = withContext(Dispatchers.IO) {
        githubService.getUsers()
            .map(userMapper::from)
            .let(::success)
    }

    override suspend fun getUserDetails(username: String): Result<UserDetailsEntity> =
        withContext(Dispatchers.IO) {
            githubService.getUser(username)
                .let(userDetailsEntityMapper::from)
                .let(::success)
        }
}