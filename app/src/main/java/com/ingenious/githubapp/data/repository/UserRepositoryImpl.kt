package com.ingenious.githubapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ingenious.githubapp.data.mapper.UserDetailsDtoMapper
import com.ingenious.githubapp.data.mapper.UserDetailsEntityMapper
import com.ingenious.githubapp.data.mapper.UserDtoMapper
import com.ingenious.githubapp.data.mapper.UserEntityMapper
import com.ingenious.githubapp.data.source.UserRemoteMediator
import com.ingenious.githubapp.data.source.local.UserDao
import com.ingenious.githubapp.data.source.local.UserDetailsDao
import com.ingenious.githubapp.data.source.remote.GithubService
import com.ingenious.githubapp.domain.model.UserDetailsEntity
import com.ingenious.githubapp.domain.model.UserEntity
import com.ingenious.githubapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val userEntityMapper: UserEntityMapper,
    private val userDtoMapper: UserDtoMapper,
    private val userDetailsEntityMapper: UserDetailsEntityMapper,
    private val userDetailsDao: UserDetailsDao,
    private val userDao: UserDao,
    private val userDetailsDtoMapper: UserDetailsDtoMapper,
) : UserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getAllUsers(): Flow<PagingData<UserEntity>> = withContext(Dispatchers.IO) {
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { userDao.getAll() },
            remoteMediator = UserRemoteMediator(
                githubService = githubService,
                userDao = userDao,
                userEntityMapper = userEntityMapper,
                userDtoMapper = userDtoMapper,
            )
        ).flow
            .map { pagedData ->
                pagedData.map { userDto ->
                    userEntityMapper.from(userDto)
                }
            }
    }

    override suspend fun getUserDetails(login: String): Result<UserDetailsEntity> =
        withContext(Dispatchers.IO) {
            val cachedUser = userDetailsDao.getUserDetails(login)
                ?.let(userDetailsEntityMapper::from)

            try {
                val userDetails = cachedUser
                    ?: githubService.getUser(login)
                        .let(userDetailsEntityMapper::from)
                        .also { userDetailsDao.insert(userDetailsDtoMapper.from(it)) }

                success(userDetails)
            } catch (e: Exception) {
                failure(e)
            }
        }
}