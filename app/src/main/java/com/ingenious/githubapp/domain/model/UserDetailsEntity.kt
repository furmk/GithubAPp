package com.ingenious.githubapp.domain.model

data class UserDetailsEntity(
    val login: String,
    val name: String,
    val location: String,
    val email: String,
    val avatarUrl: String,
)
