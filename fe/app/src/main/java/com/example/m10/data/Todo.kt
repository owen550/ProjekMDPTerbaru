package com.example.m10.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class Todo (
    val id:String,
    var content:String,
    var completed: Boolean = false,
    val createdAt: Date = Date(),
    var updatedAt: Date = Date(),
    var deletedAt: Date ?= null): Parcelable {
    init{
        if (id.length != 10){
            throw IllegalArgumentException("ID must be 10 characters")
        }
        if (content.isEmpty()){
            throw IllegalArgumentException("Content must not be empty")
        }
        for (i in id){
            if (!(i in '0'..'9' || i in 'a'..'z' || i in 'A'..'Z')){
                throw IllegalArgumentException("ID must not be alphanumeric")
            }
        }
    }
    companion object {
        private fun randomId(): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..10)
                .map { allowedChars.random() }
                .joinToString("")
        }

        fun create(content: String): Todo {
            return Todo(randomId(), content)
        }
    }
    fun copy(): Todo{
        return Todo(id, content, completed, createdAt, updatedAt,deletedAt)
    }
}