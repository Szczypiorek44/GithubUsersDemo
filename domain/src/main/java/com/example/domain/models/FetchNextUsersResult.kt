package com.example.domain.models

sealed interface FetchNextUsersResult {
    data class Success(val canFetchMoreUsers: Boolean) : FetchNextUsersResult

    data object Error : FetchNextUsersResult
}