package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecases.FetchNextUsersUseCase
import com.example.domain.usecases.ObserveUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class UserListViewModel @Inject constructor(
    private val observeUsersUseCase: ObserveUsersUseCase,
    private val fetchNextUsersUseCase: FetchNextUsersUseCase
) :
    ViewModel() {

    val userListState: StateFlow<UserListState> = observeUsersUseCase()
        .map { UserListState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = UserListState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )
}

sealed interface UserListState {
    data object Loading : UserListState

    data class Success(val userList: List<User>) : UserListState
}