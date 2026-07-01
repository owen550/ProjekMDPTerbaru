package com.example.m10.data.sources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity (tableName = "todos")
data class TodoEntity (
    @PrimaryKey(autoGenerate = false)
    val id:String,
    var content: String,
    var completed: Boolean = false,
    var createdAt: Long = Date().time,
    var updatedAt: Long = Date().time,
    var deletedAt: Long ?= null
)