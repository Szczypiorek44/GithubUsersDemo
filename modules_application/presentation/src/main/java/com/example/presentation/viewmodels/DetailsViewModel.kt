package com.example.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecases.GetUserUseCase
import com.example.presentation.viewmodels.DetailsViewModel.DetailsViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsViewModelFactory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted val userId: Int,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    @AssistedFactory
    interface DetailsViewModelFactory {
        fun create(userId: Int): DetailsViewModel
    }

    private val _detailsState = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val detailsState = _detailsState.asStateFlow()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val user = getUserUseCase(userId)
            _detailsState.value = user.toDetailsState()
        }
    }
}

sealed interface DetailsState {
    data class Success(val user: User) : DetailsState
    data object NotFound : DetailsState
    data object Loading : DetailsState
}

fun User?.toDetailsState(): DetailsState {
    return if (this == null) {
        DetailsState.NotFound
    } else {
        DetailsState.Success(this)
    }
}