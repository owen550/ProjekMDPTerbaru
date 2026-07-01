package com.example.m10.data.repositories

import com.example.m10.data.Todo
import com.example.m10.data.sources.local.LocalDataSource
import com.example.m10.data.sources.remote.RemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import net.bytebuddy.asm.Advice
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test


class DefaultTodoRepositoryTest {
    lateinit var defaultTodoRepository: DefaultTodoRepository
    lateinit var mockLocalDataSource: LocalDataSource
    lateinit var mockRemoteDataSource: RemoteDataSource
    lateinit var mockStorage:MutableList<Todo>

    @Before
    fun setUp() {
        mockLocalDataSource = mockk<LocalDataSource>()
        mockRemoteDataSource = mockk<RemoteDataSource>()
        defaultTodoRepository = DefaultTodoRepository(mockLocalDataSource, mockRemoteDataSource)
        mockStorage = mutableListOf(Todo("0000000000", "Test0"), Todo("1111111111", "Test1"))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAll()=runTest {
        coEvery {
            mockLocalDataSource.getAll()
        } returns mockStorage.toList()
        val todos = defaultTodoRepository.getAll()
        assertTrue(mockStorage.size==todos.size)
        for(i in 0..<mockStorage.size) {
            println(mockStorage[i])
            println(todos[i])
            if (mockStorage[i].id != todos[i].id || mockStorage[i].content !=todos[i].content) {
                fail()
            }
        }
        coVerify { mockLocalDataSource.getAll() }
    }

    @Test
    fun getById()=runTest{
        val contentSlot = slot<String>()
        coEvery {
            mockLocalDataSource.getById(capture(contentSlot))
        } answers {
            mockStorage.find {it.id == contentSlot.captured}
        }
        var todo = defaultTodoRepository.getById("0000000000")
        assertTrue(todo==mockStorage[0])
        coVerify{mockLocalDataSource.getById("0000000000")}
        todo = defaultTodoRepository.getById("xxxxxxxxxx")
        assertNull(todo)
        coVerify{mockLocalDataSource.getById("xxxxxxxxxx")}
    }

    // Answers harus satu baris
    @Test
    fun insert() = runTest{
        val contentSlot = slot<String>()
        val todoSlot = slot<Todo>()
        coEvery {
            mockLocalDataSource.insert(capture(contentSlot))
        } answers {
            val todo = Todo.create(contentSlot.captured)
            mockStorage.add(todo)
            todo
        }

        coEvery {
            mockRemoteDataSource.put(capture(todoSlot))
        } answers {
            todoSlot.captured
        }
        val content = "Insert Test"
        val todo = defaultTodoRepository.insert(content)
        assertTrue(mockStorage[2].content == content)
        coVerify {mockLocalDataSource.insert(content)}
        coVerify {mockRemoteDataSource.put(todo)}
    }
}
