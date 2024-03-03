package com.example.presentation.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.FetchNextUsersResult
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

    private val _fetchMoreState = MutableStateFlow<FetchMoreState>(FetchMoreState.WaitingForMore)
    val fetchMoreState = _fetchMoreState.asStateFlow()

    init {
        viewModelScope.launch {
            val firstUserList = observeUsersUseCase().first()
            if (firstUserList.isEmpty()) {
                fetchMoreUsers()
            }
        }
    }

    fun attemptFetchMoreUsers() {
        if (_fetchMoreState.value == FetchMoreState.NoMoreItems) {
            return
        }

        fetchMoreUsers()
    }

    private fun fetchMoreUsers() {
        viewModelScope.launch {
            _fetchMoreState.value = FetchMoreState.WaitingForMore
            when (val fetchResult = fetchNextUsersUseCase()) {
                is FetchNextUsersResult.Error -> {
                    _fetchMoreState.value = FetchMoreState.Error
                }

                is FetchNextUsersResult.Success -> {
                    if (fetchResult.canFetchMoreUsers) {
                        _fetchMoreState.value = FetchMoreState.WaitingForMore
                    } else {
                        _fetchMoreState.value = FetchMoreState.NoMoreItems
                    }
                }
            }
        }
    }
}

sealed interface FetchMoreState {
    data object WaitingForMore : FetchMoreState
    data object NoMoreItems : FetchMoreState
    data object Error : FetchMoreState
}