package com.ingenious.githubapp.data.mapper

import com.ingenious.githubapp.data.model.UserDetailsResponse
import com.ingenious.githubapp.domain.model.GithubUserDetails
import javax.inject.Inject

class GithubUserDetailsMapper @Inject constructor() {

    fun from(response: UserDetailsResponse): GithubUserDetails = with(response) {
        GithubUserDetails(
            name = name.orEmpty(),
            email = email.orEmpty(),
            location = location.orEmpty(),
            avatarUrl = avatarUrl.orEmpty(),
        )
    }
}