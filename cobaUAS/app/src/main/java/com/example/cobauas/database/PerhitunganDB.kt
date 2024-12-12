package com.example.cobauas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Perhitungan::class], version = 1)
abstract class PerhitunganDB: RoomDatabase() {
    abstract fun perhitunganDAO(): PerhitunganDAO

    companion object {
        @Volatile
        private var INSTANCE: PerhitunganDB? = null

        @JvmStatic
        fun getDatabase(context: Context): PerhitunganDB {
            if (INSTANCE == null) {
                synchronized(PerhitunganDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PerhitunganDB::class.java, "perhitungan_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as PerhitunganDB
        }
    }
}
