package com.example.frontend_bsua.data.sources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fe.data.source.local.UserDao
import com.example.fe.data.source.local.UserEntity

@Database(entities = [UserEntity::class], version = 2) // ganti veri == reset isi db
// Tambahkan @TypeConverters(DateConverter::class) di sini jika JadwalEntity kamu masih pakai tipe data Date
abstract class AppDatabase : RoomDatabase() { // <-- Pastikan extends RoomDatabase()

    abstract fun userDAO(): UserDao

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
                    "mdp_project" // Ini nama file database di storage
                )
                     .fallbackToDestructiveMigration() // Buka komen ini jika nanti mau mengubah struktur tabel tanpa menaikkan versi DB secara manual, lek ragu jok dibukak sek !!!
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}