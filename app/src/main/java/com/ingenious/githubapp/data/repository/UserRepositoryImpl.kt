package com.ingenious.githubapp.data.repository

import android.util.Log
import com.ingenious.githubapp.data.mapper.UserDetailsDtoMapper
import com.ingenious.githubapp.data.mapper.UserDetailsEntityMapper
import com.ingenious.githubapp.data.mapper.UserEntityMapper
import com.ingenious.githubapp.data.source.local.UserDetailsDao
import com.ingenious.githubapp.data.source.remote.GithubService
import com.ingenious.githubapp.domain.model.UserDetailsEntity
import com.ingenious.githubapp.domain.model.UserEntity
import com.ingenious.githubapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Result.Companion.success

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val userEntityMapper: UserEntityMapper,
    private val userDetailsEntityMapper: UserDetailsEntityMapper,
    private val userDetailsDao: UserDetailsDao,
    private val userDetailsDtoMapper: UserDetailsDtoMapper,
) : UserRepository {

    override suspend fun getAllUsers(): Result<List<UserEntity>> = withContext(Dispatchers.IO) {
        githubService.getUsers()
            .map(userEntityMapper::from)
            .let(::success)
    }

    override suspend fun getUserDetails(login: String): Result<UserDetailsEntity> =
        withContext(Dispatchers.IO) {
            val cachedUser = userDetailsDao.getUserDetails(login)
                ?.let(userDetailsEntityMapper::from)
                ?.also { Log.d("FURMK", "User from DB") }

            val userDetails = cachedUser
                ?: githubService.getUser(login)
                    .let(userDetailsEntityMapper::from)
                    .also { Log.d("FURMK", "User from REMOTE") }
                    .also { userDetailsDao.insert(userDetailsDtoMapper.from(it)) }

            success(userDetails)
        }
}