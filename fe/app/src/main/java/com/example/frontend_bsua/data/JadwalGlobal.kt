package com.example.frontend_bsua.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class JadwalGlobal(
    val JadwalId: Int,
    var JadwalName: String,
    var JadwalTanggalWaktu: Long,
    var JadwalStatus: String, //('BelumSelesai','Selesai')
    var createdAt: Long = Date().time,
    var updatedAt: Long = Date().time,
    var deletedAt: Long ?= null
) : Parcelable {
    init {
        if(JadwalName == ""){
            throw IllegalArgumentException("Judul Jadwal Tak Boleh Kosong")
        }
    }
}
