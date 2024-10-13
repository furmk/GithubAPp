package com.ingenious.githubapp.data.mapper

import com.ingenious.githubapp.data.model.persistance.UserDto
import com.ingenious.githubapp.domain.model.UserEntity
import javax.inject.Inject

class UserDetailsDtoMapper @Inject constructor() {

    fun from(entity: UserEntity): UserDto = with(entity) {
        UserDto(name = name)
    }
}