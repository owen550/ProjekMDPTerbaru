package com.example.frontend_bsua.data.sources.local

import com.example.frontend_bsua.data.JadwalGlobal

interface LocalDataSource {
    suspend fun getAll(): List<JadwalGlobal>
    suspend fun getById(id: Int): JadwalGlobal?
    suspend fun getOnlyStatus(status:String): List<JadwalGlobal>
    suspend fun insert(jadwal: JadwalGlobal): JadwalGlobal
    suspend fun update(jadwal: JadwalGlobal): JadwalGlobal
    suspend fun delete(jadwal: JadwalGlobal): JadwalGlobal

}

