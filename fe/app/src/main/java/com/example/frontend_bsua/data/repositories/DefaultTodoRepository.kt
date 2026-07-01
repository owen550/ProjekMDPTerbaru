package com.example.frontend_bsua.data.repositories

import com.example.frontend_bsua.data.JadwalGlobal
import com.example.frontend_bsua.data.sources.local.JadwalEntity
import com.example.frontend_bsua.data.sources.local.LocalDataSource

class DefaultTodoRepository(val localDataSource: LocalDataSource): TodoRepository {
    override suspend fun getAll(): List<JadwalGlobal> {
        return localDataSource.getAll()
    }

    override suspend fun getById(id: Int): JadwalGlobal? {
        return localDataSource.getById(id)
    }

    override suspend fun getOnlyStatus(status: String): List<JadwalGlobal> {
        return localDataSource.getOnlyStatus(status)
    }

    override suspend fun insert(jadwal: JadwalGlobal): JadwalGlobal {
        return localDataSource.insert(jadwal)
    }

    override suspend fun update(jadwal: JadwalGlobal): JadwalGlobal {
        return localDataSource.update(jadwal)
    }

    override suspend fun delete(jadwal: JadwalGlobal): JadwalGlobal {
        return  localDataSource.delete(jadwal)
    }

    override suspend fun sync() {
        TODO("Not yet implemented")
    }

}