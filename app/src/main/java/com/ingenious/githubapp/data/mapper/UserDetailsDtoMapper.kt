package com.ingenious.githubapp.data.mapper

import com.ingenious.githubapp.data.model.persistance.UserDetailsDto
import com.ingenious.githubapp.domain.model.UserDetailsEntity
import javax.inject.Inject

class UserDetailsDtoMapper @Inject constructor() {

    fun from(entity: UserDetailsEntity): UserDetailsDto = with(entity) {
        UserDetailsDto(
            login = login,
            name = name,
            location = location,
            email = email,
            avatarUrl = avatarUrl,
        )
    }
}