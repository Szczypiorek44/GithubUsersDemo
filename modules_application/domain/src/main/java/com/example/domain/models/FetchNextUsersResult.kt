package com.example.domain.models

sealed interface FetchNextUsersResult {
    data object Success : FetchNextUsersResult

    data object Error : FetchNextUsersResult
}