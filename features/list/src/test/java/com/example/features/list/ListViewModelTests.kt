package com.example.features.list

import app.cash.turbine.test
import com.example.domain.models.User
import com.example.features.list.mocks.FakeFetchNextUsersUseCase
import com.example.features.list.mocks.FakeObserveUsersUseCase
import com.example.features.list.rules.MainDispatcherRule
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


val fakeUsers = listOf(
    User(1, "John"),
    User(2, "Ann"),
    User(3, "Bob"),
    User(4, "Julia"),
    User(5, "Barbara"),
    User(6, "Artur"),
    User(7, "Robert"),
    User(8, "Agata"),
    User(9, "Sindy"),
    User(10, "Jake"),
)
