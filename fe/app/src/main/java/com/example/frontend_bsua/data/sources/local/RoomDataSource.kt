package com.example.frontend_bsua.data.sources.local

import com.example.frontend_bsua.data.JadwalGlobal
import java.util.Date

fun JadwalEntity.toJadwalGlobal(): JadwalGlobal{
    return JadwalGlobal(JadwalId,JadwalName,JadwalTanggalWaktu,JadwalStatus,createdAt,updatedAt,deletedAt)
}
fun JadwalGlobal.toJadwalEntity(): JadwalEntity{
    return JadwalEntity(JadwalId,JadwalName,JadwalTanggalWaktu,JadwalStatus,createdAt,updatedAt,deletedAt)
}

class RoomDataSource (private val db: AppDatabase): LocalDataSource{
    override suspend fun getAll(): List<JadwalGlobal> {
        return db.jadwalDao().getAll().map { it.toJadwalGlobal() }
    }

    override suspend fun getById(id: Int): JadwalGlobal? {
        var findData = db.jadwalDao().getById(id)
        if(findData != null){
            return findData.toJadwalGlobal()
        }
        else{
            return null
        }
    }

    override suspend fun getOnlyStatus(status: String): List<JadwalGlobal> {
        return db.jadwalDao().getOnlyStatus(status).map { it.toJadwalGlobal() }
    }

    override suspend fun insert(jadwal: JadwalGlobal): JadwalGlobal {
        db.jadwalDao().insert(jadwal.toJadwalEntity())
        return jadwal
    }

    override suspend fun update(jadwal: JadwalGlobal): JadwalGlobal {
        db.jadwalDao().update(jadwal.toJadwalEntity())
        return jadwal
    }

    override suspend fun delete(jadwal: JadwalGlobal): JadwalGlobal {
        val currentTimestamp = System.currentTimeMillis()
        db.jadwalDao().softDelete(jadwal.JadwalId,currentTimestamp)
        return jadwal
    }

}