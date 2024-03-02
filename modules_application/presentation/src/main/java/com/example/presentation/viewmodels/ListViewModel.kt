package com.example.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecases.FetchNextUsersUseCase
import com.example.domain.usecases.ObserveUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    observeUsersUseCase: ObserveUsersUseCase,
    private val fetchNextUsersUseCase: FetchNextUsersUseCase
) : ViewModel() {

    val userListState: StateFlow<List<User>> = observeUsersUseCase()
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    init {
        viewModelScope.launch {
            val firstUserList = observeUsersUseCase().first()
            if (firstUserList.isEmpty()) {
                fetchNextUsers()
            }
        }
    }

    fun fetchNextUsers() {
        viewModelScope.launch {
            _loadingState.value = true
            val fetchResult = fetchNextUsersUseCase()
            if (fetchResult is Error) {
                //TODO show error
            }
            _loadingState.value = false
        }
    }
}