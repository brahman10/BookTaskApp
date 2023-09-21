package com.traktez.findfalcon.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("users")
data class LoginEntity(
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "country")
    val country: String
)