package com.traktez.findfalcon.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.traktez.findfalcon.data.api.CoroutineApiService
import com.traktez.findfalcon.data.db.AppDB
import com.traktez.findfalcon.data.entity.BookItemEntity
import com.traktez.findfalcon.data.entity.FavouriteBooksEntity
import com.traktez.findfalcon.data.entity.State
import com.traktez.findfalcon.ui.adapters.BookListAdapter
import com.traktez.findfalcon.ui.base.BaseViewModel
import com.traktez.findfalcon.utils.requestAwait
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val apiService: CoroutineApiService,
    private val db: AppDB
) : BaseViewModel() {
    private var favouriteBooks = arrayListOf<FavouriteBooksEntity>()

    val booksLiveData = MutableLiveData<State<ArrayList<BookItemEntity>>>()

    private val adapterList = arrayListOf<BookItemEntity>()

    @Inject
    lateinit var bookListAdapter: BookListAdapter

    var sortBy = ""
    var isAscending = true
    fun getData() {
        viewModelScope.launch {
            favouriteBooks = db.bookDao().getFavouriteBooks() as ArrayList
            booksLiveData.value = requestAwait { apiService.getBookListAsync() }.toState()
        }
    }

    fun sortAdapterList() {
        when (sortBy) {
            "title" -> {
                adapterList.sortWith { o1, o2 ->
                    o1.title.uppercase(Locale.ROOT).compareTo(
                        o2.title.uppercase(Locale.ROOT)
                    )
                }
            }

            "hits" -> {
                adapterList.sortWith { o1, o2 ->
                    o1.hits.compareTo(
                        o2.hits
                    )
                }
            }

            else -> {
                adapterList.sortWith { o1, o2 ->
                    o1.isBookMarked.compareTo(
                        o2.isBookMarked
                    )
                }
                adapterList.reverse()
            }
        }
        if (!isAscending) {
            adapterList.reverse()
        }
        bookListAdapter.setBookList(adapterList)
    }

    fun updateSorting() {
        adapterList.reverse()
        bookListAdapter.setBookList(adapterList)
    }

    fun updateAdapterList(list: ArrayList<BookItemEntity>) {
        list.forEach { remoteBooks ->
            favouriteBooks.forEach { favouriteBooks ->
                if (remoteBooks.id == favouriteBooks.id) {
                    remoteBooks.isBookMarked = true
                }
            }
        }
        adapterList.clear()
        adapterList.addAll(list)
        bookListAdapter.setBookList(adapterList)
    }


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

//    fun clearDB(){
//        viewModelScope.launch {
//            db.clearAllTables()
//        }
//    }

}