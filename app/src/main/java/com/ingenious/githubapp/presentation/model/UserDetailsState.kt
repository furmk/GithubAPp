package com.ingenious.githubapp.presentation.model

import com.ingenious.githubapp.domain.model.UserDetailsEntity

sealed interface UserDetailsState {

    data object Loading : UserDetailsState

    data class Loaded(val userDetails: UserDetailsEntity) : UserDetailsState
}
