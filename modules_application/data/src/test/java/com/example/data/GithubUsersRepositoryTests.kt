package com.example.data

import app.cash.turbine.test
import com.example.data.mocks.FakeGithubApi
import com.example.data.mocks.FakeUserDao
import com.example.data.mocks.fakeGithubUsers
import com.example.data.utils.asDomainModelList
import com.example.data.utils.toUserEntityList
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GithubUsersRepositoryTests {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val fakeGithubApi = FakeGithubApi(
        onDownloadReturn = { Response.success(fakeGithubUsers) })

    private val fakeUserDao = FakeUserDao()

    private val promoCodesRepository = GithubUsersRepositoryImpl(
        githubApi = fakeGithubApi,
        userDao = fakeUserDao,
        ioDispatcher = UnconfinedTestDispatcher(),
        scope = testScope
    )

    @Test
    fun when_daoEmits_expect_newObservedUsers() = runTest {
        val usersFlow = promoCodesRepository.observeUsers()

        val fakeEntities = fakeGithubUsers.toUserEntityList()

        usersFlow.test {
            fakeUserDao.emit(fakeEntities)

            assertEquals(fakeEntities.asDomainModelList(), awaitItem())
        }
    }

    @Test
    fun when_daoDoesntEmit_expect_noUsersObserved() = runTest {
        val usersFlow = promoCodesRepository.observeUsers()

        usersFlow.test {
            expectNoEvents()
        }
    }

    @Test
    fun when_daoEmitsMultiple_expect_multipleUsersObserved() = runTest {
        val usersFlow = promoCodesRepository.observeUsers()

        val fakeEntities = fakeGithubUsers.toUserEntityList()

        usersFlow.test {
            fakeUserDao.emit(fakeEntities)

            assertEquals(fakeEntities.asDomainModelList(), awaitItem())

            fakeUserDao.emit(fakeEntities.subList(0, 3))
            assertEquals(fakeEntities.subList(0, 3).asDomainModelList(), awaitItem())

            fakeUserDao.emit(fakeEntities.subList(0, 9))
            assertEquals(fakeEntities.subList(0, 9).asDomainModelList(), awaitItem())

            expectNoEvents()
        }
    }
}