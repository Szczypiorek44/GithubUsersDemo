package com.example.domain.usecases

import com.example.domain.models.User
import com.example.domain.repositories.GithubUsersRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

const val COUNTRY_UNITED_STATES = "United States"

internal class GetFilteredUsersUseCaseImpl @Inject constructor(
    private val githubUsersRepository: GithubUsersRepository
) : GetFilteredUsersUseCase {

    private val countryComparator: (User) -> Comparable<*> = { it.countryName }
    private val stateComparator: (User) -> Comparable<*> = { it.stateName }
    private val nameComparator: (User) -> Comparable<*> = { it.name }

    override suspend fun invoke(filterInput: String): List<User> {
        val users = githubUsersRepository.observeUsers()
            .first()
            .filter { it.name.contains(filterInput) }

        val (usersFromUsa, restOfTheUsers) = users.partition { it.countryName == COUNTRY_UNITED_STATES }

        return usersFromUsa.sortByStateAndName() +
                restOfTheUsers.sortByCountryStateAndName()
    }

    private fun List<User>.sortByStateAndName(): List<User> {
        return sortedWith(compareBy(stateComparator, nameComparator))
    }

    private fun List<User>.sortByCountryStateAndName(): List<User> {
        return sortedWith(compareBy(countryComparator, stateComparator, nameComparator))
    }
}