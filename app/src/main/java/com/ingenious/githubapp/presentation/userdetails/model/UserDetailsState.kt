package com.ingenious.githubapp.presentation.userdetails.model

import com.ingenious.githubapp.domain.model.UserDetailsEntity

sealed interface UserDetailsState {

    data object Loading : UserDetailsState

    data object Error : UserDetailsState

    data class Loaded(val userDetails: UserDetailsEntity) : UserDetailsState
}
