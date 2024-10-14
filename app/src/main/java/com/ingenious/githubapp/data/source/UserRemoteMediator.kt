package com.ingenious.githubapp.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ingenious.githubapp.data.mapper.UserDtoMapper
import com.ingenious.githubapp.data.mapper.UserEntityMapper
import com.ingenious.githubapp.data.model.persistance.UserDto
import com.ingenious.githubapp.data.source.local.UserDao
import com.ingenious.githubapp.data.source.remote.GithubService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val githubService: GithubService,
    private val userDao: UserDao,
    private val userEntityMapper: UserEntityMapper,
    private val userDtoMapper: UserDtoMapper,
) : RemoteMediator<Int, UserDto>() {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserDto>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItemId = userDao.getLastUserId()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    lastItemId
                }
            }

            val users = githubService.getUsers(
                since = page,
                perPage = state.config.pageSize
            ).map(userEntityMapper::from)

            userDao.insertAll(*users.map(userDtoMapper::from).toTypedArray())

            MediatorResult.Success(endOfPaginationReached = users.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}