package com.ingenious.githubapp.data.mapper

import com.ingenious.githubapp.data.model.persistance.UserDto
import com.ingenious.githubapp.data.model.response.UserResponse
import com.ingenious.githubapp.domain.model.UserEntity
import javax.inject.Inject

class UserEntityMapper @Inject constructor() {

    fun from(response: UserResponse): UserEntity = with(response) {
        UserEntity(
            id = id,
            login = login,
        )
    }

    fun from(dto: UserDto) = with(dto) {
        UserEntity(
            id = id,
            login = login,
        )
    }
}
