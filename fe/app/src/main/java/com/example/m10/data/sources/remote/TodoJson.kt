package com.example.m10.data.sources.remote

import java.util.Date

class TodoJson (
    val id:String,
    var content: String,
    var completed: Boolean = false,
    var createdAt: Long = Date().time,
    var updatedAt: Long = Date().time,
    var deletedAt: Long ?= null
)