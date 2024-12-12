package com.example.cobauas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Perhitungan(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nomorHP")
    var nomorHP: String,

    @ColumnInfo(name = "nama")
    var nama: String,

    @ColumnInfo(name = "berat")
    var berat: Int

)
