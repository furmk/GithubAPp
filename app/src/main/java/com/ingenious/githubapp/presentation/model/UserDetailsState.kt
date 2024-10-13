package com.ingenious.githubapp.presentation.model

import com.ingenious.githubapp.domain.model.GithubUserDetails

sealed interface UserDetailsState {

    data object Loading : UserDetailsState

    data class Loaded(val userDetails: GithubUserDetails) : UserDetailsState
}
