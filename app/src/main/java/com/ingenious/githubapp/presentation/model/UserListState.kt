package com.ingenious.githubapp.presentation.model

import com.ingenious.githubapp.domain.model.GithubUser

data class UserListState(
    val usersList: List<GithubUser> = emptyList()
)