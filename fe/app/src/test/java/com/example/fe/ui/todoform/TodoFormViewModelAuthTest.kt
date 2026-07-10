package com.example.fe.ui.todoform

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fe.data.User
import com.example.fe.data.repositories.TodoRepository
import com.example.fe.user
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class TodoFormViewModelAuthTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: TodoFormViewModel

    @Before
    fun setUp() {
        user = null
        currentUserId = null
    }

    @After
    fun tearDown() {
        user = null
        currentUserId = null
    }

    @Test
    fun doLogin_success_setsLoginTrueAndCurrentUser() = runTest {
        val dummyUser = sampleUser(id = 1)
        val repository = mockk<TodoRepository>()
        coEvery { repository.doLogin("tester", "secret") } returns Result.success(dummyUser)
        viewModel = TodoFormViewModel(repository)

        viewModel.doLogin("tester", "secret")
        advanceUntilIdle()

        assertTrue(viewModel.login.value == true)
        assertEquals(1, currentUserId)
        assertEquals(dummyUser, user)
        assertTrue(viewModel.loading.value == false)
    }

    @Test
    fun doLogin_failure_setsErrorMessage() = runTest {
        val repository = mockk<TodoRepository>()
        coEvery { repository.doLogin("tester", "wrong") } returns Result.failure(Exception("invalid"))
        viewModel = TodoFormViewModel(repository)

        viewModel.doLogin("tester", "wrong")
        advanceUntilIdle()

        assertEquals("Username/Email atau Password Salah !!!", viewModel.message.value)
        assertTrue(viewModel.login.value == false)
        assertTrue(viewModel.loading.value == false)
    }

    @Test
    fun register_success_setsSuccessMessage() = runTest {
        val registeredUser = sampleUser(id = 2)
        val repository = mockk<TodoRepository>()
        coEvery {
            repository.addUser(any())
        } returns Result.success(registeredUser)
        viewModel = TodoFormViewModel(repository)

        viewModel.register(
            name = "Budi",
            username = "budi",
            email = "budi@mail.com",
            password = "secret",
            birthdayDate = "2000-01-01"
        )
        advanceUntilIdle()

        assertEquals("User berhasil register", viewModel.message.value)
        assertEquals(registeredUser, user)
        assertTrue(viewModel.loading.value == false)
    }

    @Test
    fun register_failure_setsRepositoryErrorMessage() = runTest {
        val repository = mockk<TodoRepository>()
        coEvery {
            repository.addUser(any())
        } returns Result.failure(Exception("Email already used"))
        viewModel = TodoFormViewModel(repository)

        viewModel.register(
            name = "Budi",
            username = "budi",
            email = "budi@mail.com",
            password = "secret",
            birthdayDate = "2000-01-01"
        )
        advanceUntilIdle()

        assertEquals("Email already used", viewModel.message.value)
        assertTrue(viewModel.loading.value == false)
    }

    private fun sampleUser(id: Int): User = User(
        id = id,
        name = "Test User",
        username = "tester",
        password = "secret",
        email = "tester@mail.com",
        birthday_date = "2000-01-01",
        role = "student",
        tier = "free",
        google_id = "",
        created_at = "",
        updated_at = "",
        deleted_at = "",
        points = 0
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
