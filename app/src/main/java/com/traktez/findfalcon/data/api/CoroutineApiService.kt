package com.traktez.findfalcon.data.api

import com.traktez.findfalcon.data.entity.BookItemEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface CoroutineApiService {

    @GET("b/ZEDF")
    fun getBookListAsync(): Deferred<ArrayList<BookItemEntity>>

}