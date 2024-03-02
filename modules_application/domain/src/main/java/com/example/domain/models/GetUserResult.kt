package com.example.domain.models

sealed interface GetUserResult {
    data class Success(val user: User) : GetUserResult
    data class Error(val exception: Throwable) : GetUserResult
    data object Loading : GetUserResult
}