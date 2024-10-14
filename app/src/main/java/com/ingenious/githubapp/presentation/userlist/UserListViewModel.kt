package com.ingenious.githubapp.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ingenious.githubapp.domain.model.UserEntity
import com.ingenious.githubapp.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<UserEntity>>(PagingData.empty())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllUsersUseCase.run()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { usersPaged ->
                    _uiState.update { usersPaged }
                }
        }
    }
}