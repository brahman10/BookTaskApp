package com.traktez.findfalcon.ui.fragments

import androidx.lifecycle.viewModelScope
import com.traktez.findfalcon.data.db.AppDB
import com.traktez.findfalcon.data.entity.FavouriteBooksEntity
import com.traktez.findfalcon.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val db: AppDB
) :BaseViewModel(){

    fun bookMarkBook(id: String) {
        viewModelScope.launch {
            db.bookDao().addFavouriteBook(FavouriteBooksEntity(id))
        }
    }

    fun unBookMarkBook(id: String) {
        viewModelScope.launch {
            db.bookDao().removeFavouriteBook(id)
        }
    }
}