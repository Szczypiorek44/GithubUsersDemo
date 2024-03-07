package com.example.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecases.GetFilteredUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getFilteredUsersUseCase: GetFilteredUsersUseCase
) : ViewModel() {

    private val _userListState = MutableStateFlow<List<User>>(emptyList())
    val userListState = _userListState.asStateFlow()

    init {
        searchUsersBy("")
    }

    fun searchUsersBy(input: String) {
        viewModelScope.launch {
            _userListState.value = getFilteredUsersUseCase(input)
        }
    }
}