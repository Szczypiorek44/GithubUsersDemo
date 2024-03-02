package com.example.presentation.viewmodels

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
class ListViewModel @Inject constructor(
    private val observeUsersUseCase: ObserveUsersUseCase,
    private val fetchNextUsersUseCase: FetchNextUsersUseCase
) : ViewModel() {

    val listState: StateFlow<ListState> = observeUsersUseCase()
        .map { ListState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = ListState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )
}

sealed interface ListState {
    data object Loading : ListState

    data class Success(val userList: List<User>) : ListState
}