package com.ingenious.githubapp.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingenious.githubapp.domain.usecase.GetAllUsersUseCase
import com.ingenious.githubapp.presentation.model.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserListState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllUsersUseCase.run()
                .onSuccess { users ->
                    _uiState.update {
                        it.copy(
                            usersList = users
                        )
                    }
                }
        }
    }
}