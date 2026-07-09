package com.example.fe.data.source.local

import com.example.fe.data.User
import com.example.frontend_bsua.data.sources.local.AppDatabase

// ===== user ==========
fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        username = username,
        password = password,
        email = email,
        google_id = google_id,
        birthday_date = birthday_date,

        // iki aku g ro soale kan kosong, lek ada sing nduew saran silahkan ganti
        role = "",
        tier = null,
        points = null,
        created_at = null,
        updated_at = null,
        deleted_at = null
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        username = username,
        password = password,
        email = email,
        google_id = google_id,
        birthday_date = birthday_date
    )
}

// ====== room =========
class RoomDataSource(
    private val db: AppDatabase
) : LocalDataSource {

    override suspend fun getAll(): List<User> {
        return db.userDAO().getAll().map { it.toUser() }
    }

    override suspend fun getById(id: Int): User? {
        return db.userDAO().getById(id)?.toUser()
    }

    override suspend fun getLastUserDESC(): User {
        return db.userDAO().getLastUserDESC()?.toUser()
            ?: throw NoSuchElementException("User tidak ditemukan")
    }

    override suspend fun insert(user: User): User {
        db.userDAO().insert(user.toEntity())
        return user
    }

    override suspend fun update(user: User): User {
        db.userDAO().update(user.toEntity())
        return user
    }

    override suspend fun delete(jadwal: User): User {
        db.userDAO().delete(jadwal.toEntity())
        return jadwal
    }
}