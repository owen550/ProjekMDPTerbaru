package com.example.frontend_bsua.data.repositories

import com.example.frontend_bsua.data.JadwalGlobal
import com.example.frontend_bsua.data.sources.local.JadwalEntity

interface TodoRepository {
    suspend fun getAll():List<JadwalGlobal>
    suspend fun getById(id: Int):JadwalGlobal?
    suspend fun getOnlyStatus(status: String):List<JadwalGlobal>
    suspend fun insert(jadwal: JadwalGlobal): JadwalGlobal
    suspend fun update(jadwal: JadwalGlobal): JadwalGlobal
    suspend fun delete(jadwal: JadwalGlobal): JadwalGlobal
    suspend fun sync()
}
