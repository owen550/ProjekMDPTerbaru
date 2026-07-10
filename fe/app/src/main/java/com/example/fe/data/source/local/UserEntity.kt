package com.example.fe.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
<<<<<<< Updated upstream
    val id: Int = 0,
=======
    val id: Int? = null,
>>>>>>> Stashed changes
    val name: String,
    val username: String? = null,
    val password: String? = null,
    val email: String,
    val google_id: String? = null,
    val birthday_date: String? = null,
)


