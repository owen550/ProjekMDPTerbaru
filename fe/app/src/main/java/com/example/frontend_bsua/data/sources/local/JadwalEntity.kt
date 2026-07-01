package com.example.frontend_bsua.data.sources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity (tableName = "jadwal")
data class JadwalEntity(
    @PrimaryKey(autoGenerate = true)
    val JadwalId: Int,
    var JadwalName: String,
    var JadwalTanggalWaktu: Long,
    var JadwalStatus: String, //('BelumSelesai','Selesai')

    var createdAt: Long = Date().time,
    var updatedAt: Long = Date().time,
    var deletedAt: Long ?= null
)
