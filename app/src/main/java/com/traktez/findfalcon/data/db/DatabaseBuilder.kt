package com.traktez.findfalcon.data.db

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: AppDB? = null
    private const val DB_NAME = "APP_DB"

    fun getInstance(context: Context): AppDB {
        if (INSTANCE == null) {
            synchronized(AppDB::class) {
                if (INSTANCE == null) {
                    INSTANCE = buildRoomDB(context)
                }
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDB::class.java,
            DB_NAME
        ).build()

}