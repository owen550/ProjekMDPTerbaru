package com.example.frontend_bsua.data.sources.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface JadwalDAO {
    @Query("select * from jadwal where deletedAt is null order by createdAt desc")
    suspend fun getAll(): List<JadwalEntity>

    @Query("select * from jadwal where JadwalId = :id and deletedAt is null")
    suspend fun getById(id: Int): JadwalEntity?

    @Query("select * from jadwal where JadwalStatus = :status and deletedAt is null order by createdAt desc")
    suspend fun getOnlyStatus(status: String): List<JadwalEntity>

    @Insert
    suspend fun insert(jadwal: JadwalEntity)

    @Update
    suspend fun update(jadwal: JadwalEntity)

    @Query("update jadwal set deletedAt = :timestamp where JadwalId = :id")
    suspend fun softDelete(id: Int, timestamp: Long)
}


