package com.example.m10

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    // test bahwa fragment yang pertama muncul adalah fragment Todos
    @Test
    fun showTodosFragmentFirst(){
        onView(withId(R.id.constraint_todos))
            .check(matches(isDisplayed()))
    }
    // test bahwa ketika tombol add diklik pindah ke halaman form
    @Test
    fun navigateFromTodosToTodoForm(){
        onView(withId(R.id.add_btn_todos))
            .perform(click())
        onView(withId(R.id.constraint_todo_form))
            .check(matches(isDisplayed()))
    }
    // test bahwa ketika tombol cancel diklik kembali ke halaman Todos
    @Test
    fun navigateFromTodoFormToTodos(){
        onView(withId(R.id.add_btn_todos))
            .perform(click())
        onView(withId(R.id.constraint_todo_form))
            .check(matches(isDisplayed()))
        onView(withId(R.id.cancel_btn_todoform))
            .perform(click())
        onView(withId(R.id.constraint_todos))
            .check(matches(isDisplayed()))
    }
}
