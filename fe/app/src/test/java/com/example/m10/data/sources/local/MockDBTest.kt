package com.example.m10.data.sources.local

import com.example.m10.data.Todo
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class MockDBTest {
    @Before
    fun setUp() {
      MockDB.todos.clear()
        MockDB.todos.add(Todo("000000000", "test0"))
        MockDB.todos.add(Todo("1111111111", "Test1"))
        MockDB.todos.add(Todo("2222222222", "Test2"))
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getAll() = runTest {
        val todos = MockDB.getAll()
        assertTrue(todos.size == 3)
        assertTrue(todos[0].id == "0000000000")
        assertTrue(todos[0].content == "Test0")
        assertTrue(todos[1].id == "1111111111")
        assertTrue(todos[1].content == "Test1")
        assertTrue(todos[2].id == "2222222222")
        assertTrue(todos[2].content == "Test2")
    }

    @Test
    fun getById() = runTest {
        var todo = MockDB.getById("0000000000")
        assertTrue(todo?.id == "0000000000")
        assertTrue(todo?.content == "Test0")
        todo = MockDB.getById("3333333333")
        assertNull(todo)
    }
    @Test
    fun insert() = runTest {
        MockDB.insert("Insert test")
        var todos = MockDB.getAll()
        assertTrue(todos.size == 4)
        assertTrue(todos[3].content == "Insert test")
    }
}
