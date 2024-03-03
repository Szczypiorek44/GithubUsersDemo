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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = DetailsViewModelFactory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted val userId: Int,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    @AssistedFactory
    interface DetailsViewModelFactory {
        fun create(userId: Int): DetailsViewModel
    }

    val detailsState = flow {
        val user = getUserUseCase(userId)
        if (user != null) {
            emit(DetailsState.Success(user))
        } else {
            emit(DetailsState.NotFound)
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = DetailsState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}

sealed interface DetailsState {
    data class Success(val user: User) : DetailsState
    data object NotFound : DetailsState
    data object Loading : DetailsState
}