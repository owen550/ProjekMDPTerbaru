package com.example.frontend_bsua.data.sources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [JadwalEntity::class], version = 1)
// Tambahkan @TypeConverters(DateConverter::class) di sini jika JadwalEntity kamu masih pakai tipe data Date
abstract class AppDatabase : RoomDatabase() { // <-- Pastikan extends RoomDatabase()

    abstract fun jadwalDao(): JadwalDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Jika instance sudah ada, langsung kembalikan
            return INSTANCE ?: synchronized(this) {
                // Jika belum ada, buat database baru
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bsua_database" // Ini nama file database di storage kamu
                )
                    // .fallbackToDestructiveMigration() // Buka komen ini jika nanti kamu mengubah struktur tabel tanpa menaikkan versi DB secara manual
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}