package com.ingenious.githubapp.data.mapper

import com.ingenious.githubapp.data.model.UserResponse
import com.ingenious.githubapp.domain.model.GithubUser
import javax.inject.Inject

class GithubUserMapper @Inject constructor() {

    fun from(response: UserResponse): GithubUser = with(response) {
        GithubUser(login = login)
    }
}
