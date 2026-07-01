package com.example.m10.data.sources.local

import com.example.m10.data.Todo
import java.util.Date

fun TodoEntity.toTodo(): Todo{
    return Todo(id, content, completed, Date(createdAt), Date(updatedAt), deletedAt?.let { Date(it) })
}

fun Todo.toTodoEntity(): TodoEntity{
    return TodoEntity(id, content, completed, createdAt.time, updatedAt.time, deletedAt?.time)
}

class RoomDataSource(private val db: AppDatabase): LocalDataSource{
    override suspend fun getAll(): List<Todo>{
        return db.todoDao().getAll().map { it.toTodo()}
    }

    override suspend fun getById(id: String): Todo? {
        val todoEntity = db.todoDao().getById(id)
        if (todoEntity != null){
            return todoEntity.toTodo()
        }else{
            return null
        }
    }

    override suspend fun insert(content: String): Todo {
        val todo = Todo.create(content)
        db.todoDao().insert(todo.toTodoEntity())
        return todo
    }

    override suspend fun update(
        id: String,
        content: String,
        completed: Boolean
    ): Todo {
        val todoEntity = db.todoDao().getById(id)
        if (todoEntity != null){
            todoEntity.content = content
            todoEntity.completed = completed
            todoEntity.updatedAt = Date().time
            db.todoDao().update(todoEntity)
            return todoEntity.toTodo()
        }else{
            throw NoSuchElementException("Todo with ID $id not found")
        }

    }

    override suspend fun delete(id: String): Todo {
        val todoEntity = db.todoDao().getById(id)
        if (todoEntity != null) {
            todoEntity.updatedAt = Date().time
            db.todoDao().update(todoEntity)
            return todoEntity.toTodo()
        }else{
            throw NoSuchElementException("Todo with ID $id not found")
        }
    }

    override suspend fun getUnsynced(): List<Todo> {
        return db.todoDao().getUnsynced().map { it.toTodo() }
    }

    override suspend fun sync(todos: List<Todo>) {
        db.todoDao().deleteAll()
        db.todoDao().bulkInsert(todos.map { it.toTodoEntity() })
    }
}