package com.traktez.findfalcon.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.traktez.findfalcon.data.entity.FavouriteBooksEntity


@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteBook(favouriteBooksEntity: FavouriteBooksEntity)

    @Query("SELECT * from `favourite_books`")
    suspend fun getFavouriteBooks(): List<FavouriteBooksEntity>

    @Query("DELETE FROM `favourite_books` WHERE id LIKE  :bookId")
    suspend fun removeFavouriteBook(bookId: String)
}