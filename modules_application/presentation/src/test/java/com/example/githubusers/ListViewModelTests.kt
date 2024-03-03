package com.example.githubusers

import app.cash.turbine.test
import com.example.domain.models.User
import com.example.githubusers.mocks.FakeFetchNextUsersUseCase
import com.example.githubusers.mocks.FakeObserveUsersUseCase
import com.example.githubusers.mocks.fakeUsers
import com.example.githubusers.rules.MainDispatcherRule
import com.example.presentation.features.list.ListViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTests {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun when_viewModelInitialized_expect_emptyUsers() = runTest {
        val observeUsersUseCase = FakeObserveUsersUseCase()
        val fetchNextUsersUseCase = FakeFetchNextUsersUseCase()
        val viewModel = ListViewModel(observeUsersUseCase, fetchNextUsersUseCase)

        viewModel.userListState.test {
            assertEquals(emptyList<User>(), awaitItem())
        }
    }

    @Test
    fun when_useCaseEmits_expect_stateCollectsUsers() = runTest {
        val observeUsersUseCase = FakeObserveUsersUseCase()
        val fetchNextUsersUseCase = FakeFetchNextUsersUseCase()
        val viewModel = ListViewModel(observeUsersUseCase, fetchNextUsersUseCase)

        viewModel.userListState.test {
            assertEquals(emptyList<User>(), awaitItem())

            observeUsersUseCase.emit(fakeUsers.subList(0,3))
            assertEquals(fakeUsers.subList(0,3), awaitItem())

            observeUsersUseCase.emit(fakeUsers.subList(4,8))
            assertEquals(fakeUsers.subList(4,8), awaitItem())
        }
    }
}