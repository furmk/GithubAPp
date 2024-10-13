package com.ingenious.githubapp.domain.repository

import com.ingenious.githubapp.domain.model.GithubUser
import com.ingenious.githubapp.domain.model.GithubUserDetails

interface UserRepository {

    suspend fun getAllUsers(): Result<List<GithubUser>>

    suspend fun getUserDetails(username: String) : Result<GithubUserDetails>
}