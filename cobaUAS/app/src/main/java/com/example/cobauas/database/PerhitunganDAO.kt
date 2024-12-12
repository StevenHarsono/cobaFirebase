package com.example.cobauas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PerhitunganDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(perhitungan: Perhitungan)

    @Query("SELECT * FROM perhitungan WHERE nomorHP = :nomorHP")
    fun getPerhitungan(nomorHP: String): List<Perhitungan>

    @Query("SELECT * FROM perhitungan")
    fun getAllPerhitungan(): List<Perhitungan>

    @Delete
    fun delete(perhitungan: Perhitungan)
}