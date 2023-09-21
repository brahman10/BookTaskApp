package com.traktez.findfalcon.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookItemEntity(
    @SerializedName("id") var id: String,
    @SerializedName("image") var image: String,
    @SerializedName("hits") var hits: Int,
    @SerializedName("alias") var alias: String,
    @SerializedName("title") var title: String,
    @SerializedName("lastChapterDate") var lastChapterDate: Long,
    var isBookMarked: Boolean = false
) : Parcelable

@Entity("favourite_books")
data class FavouriteBooksEntity(
    @ColumnInfo("id")
    @PrimaryKey
    val id: String
)