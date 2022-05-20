package com.enterprise.books.interfaces

import com.enterprise.books.models.BooksData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NytimesApi {

    @GET("lists/{date}/{list}.json")
    fun getBooks(@Path("date") date: String,
                 @Path("list") list: String,
                 @Query("api-key") apiKey: String): Call<BooksData>

}