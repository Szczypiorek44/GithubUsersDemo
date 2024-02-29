package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetGithubUsersUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class UserListViewModel(private val githubUsersUseCase: GetGithubUsersUseCase) : ViewModel() {

    val userListState: StateFlow<UserListState> = githubUsersUseCase()
        .map { UserListState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = UserListState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )
}

sealed interface UserListState {
    data object Loading : UserListState

    data class Success(val userList: List<String>) : UserListState
}