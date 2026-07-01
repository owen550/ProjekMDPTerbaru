package com.example.m10.data

import org.junit.Assert.*
import org.junit.Test
import java.util.Date

class TodoTest {
    @Test
    fun `Todo datetime correct`(){
        val currentTime = Date().time
        val todo = Todo("1234567890", "Test bikin claw yang banyak")
        assertTrue((currentTime - todo.createdAt.time) <= 1000)
        assertTrue((currentTime - todo.updatedAt.time) <= 1000)
    }

    @Test
    fun `Todo 10 alphanumeric character ID`(){
        val todo = Todo.create("Test Bikin Claw yang Banyak")
        assertTrue("ID Length is not 10", todo.id.length == 10)
        for (i in todo.id){
            assertTrue("ID is not alphanumeric", i.isLetterOrDigit())
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Todo ID must be 10 alphanumeric character String`(){
        Todo("", "Test bikin claw yang BUanyak")
        Todo("123456789", "Test bikin claw yang BUanyak")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Todo content must not be empty`(){
        Todo("123456789", "")
    }
}