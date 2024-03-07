package com.example.domain.usecases

import com.example.domain.models.User
import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

const val COUNTRY_UNITED_STATES = "United States"

internal class GetFilteredUsersUseCaseImpl @Inject constructor(
    private val githubUsersRepository: GithubUsersRepository
) : GetFilteredUsersUseCase {
    override suspend fun invoke(filterInput: String): List<User> {
        val users = githubUsersRepository.observeUsers()
            .first()
            .filter { it.name.contains(filterInput) }


        val (usersFromUsa, rest) = users.partition { it.countryName == COUNTRY_UNITED_STATES }

        return usersFromUsa.sortByCountryStateAndName() + rest.sortByCountryStateAndName()
    }

    private fun List<User>.sortByCountryStateAndName(): List<User> {
        return sortedWith(compareBy({ it.countryName }, { it.stateName }, { it.name }))
    }

}