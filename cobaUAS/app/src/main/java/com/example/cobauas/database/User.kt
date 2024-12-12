package com.example.cobauas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    @ColumnInfo(name = "nomorHP")
    var nomorHP: String,

    @ColumnInfo(name = "password")
    var password: String
)
