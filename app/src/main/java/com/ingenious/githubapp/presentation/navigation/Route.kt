package com.ingenious.githubapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object UserList : Route()

    @Serializable
    data class UserDetail(val username: String) : Route()
}
