package com.example.domain.usecases

import com.example.domain.models.User

interface GetFilteredUsersUseCase {
    suspend operator fun invoke(filterInput: String): List<User>

}