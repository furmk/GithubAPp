package com.ingenious.githubapp.presentation.model

import com.ingenious.githubapp.domain.model.UserEntity

data class UserListState(
    val usersList: List<UserEntity> = emptyList()
)