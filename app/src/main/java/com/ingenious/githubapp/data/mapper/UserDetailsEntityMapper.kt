package com.ingenious.githubapp.data.mapper

import com.ingenious.githubapp.data.model.persistance.UserDetailsDto
import com.ingenious.githubapp.data.model.response.UserDetailsResponse
import com.ingenious.githubapp.domain.model.UserDetailsEntity
import javax.inject.Inject

class UserDetailsEntityMapper @Inject constructor() {

    fun from(response: UserDetailsResponse): UserDetailsEntity = with(response) {
        UserDetailsEntity(
            login = login,
            name = name.orEmpty(),
            email = email.orEmpty(),
            location = location.orEmpty(),
            avatarUrl = avatarUrl.orEmpty(),
        )
    }

    fun from(dto: UserDetailsDto): UserDetailsEntity = with(dto) {
        UserDetailsEntity(
            login = login,
            name = name,
            email = email,
            location = location,
            avatarUrl = avatarUrl,
        )
    }
}