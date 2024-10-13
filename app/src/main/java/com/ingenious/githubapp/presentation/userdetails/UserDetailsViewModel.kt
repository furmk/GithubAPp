package com.ingenious.githubapp.presentation.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingenious.githubapp.domain.usecase.GetUserDetailsUseCase
import com.ingenious.githubapp.presentation.model.UserDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailsState>(UserDetailsState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getUserDetails(login: String) {
        login.let {
            viewModelScope.launch {
                getUserDetailsUseCase.run(login)
                    .onSuccess { userDetails ->
                        _uiState.update {
                            UserDetailsState.Loaded(userDetails)
                        }
                    }
            }
        }
    }
}