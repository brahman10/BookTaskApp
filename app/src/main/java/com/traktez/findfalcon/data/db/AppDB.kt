package com.traktez.findfalcon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.traktez.findfalcon.data.db.dao.BookDao
import com.traktez.findfalcon.data.db.dao.LoginDao
import com.traktez.findfalcon.data.entity.FavouriteBooksEntity
import com.traktez.findfalcon.data.entity.LoginEntity


@Database(entities = [LoginEntity::class, FavouriteBooksEntity::class], version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun loginDao(): LoginDao

    abstract fun bookDao(): BookDao

}