package com.traktez.findfalcon.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.traktez.findfalcon.data.entity.LoginEntity


@Dao
interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(loginEntity: LoginEntity): Long

    @Query("SELECT * from `USERS` WHERE name LIKE  :name and password LIKE  :password")
    suspend fun getUser(name:String, password:String): LoginEntity?

}