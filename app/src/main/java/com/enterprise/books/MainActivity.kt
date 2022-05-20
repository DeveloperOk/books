package com.enterprise.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.enterprise.books.constants.NytimesApiConstants
import com.enterprise.books.interfaces.NytimesApi
import com.enterprise.books.models.AppBook
import com.enterprise.books.models.BooksData
import com.enterprise.books.models.BuyLink
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var appBooks: ArrayList<AppBook> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getBooks()
    }

    private fun getBooks() {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(NytimesApiConstants.BaseUrl)
            .build()


        val nytimesApi: NytimesApi  = retrofitBuilder.create(NytimesApi::class.java)

        val retrofitData = nytimesApi.getBooks("current", "hardcover-fiction", NytimesApiConstants.ApiKey)

        retrofitData.enqueue(object : Callback<BooksData?> {
            override fun onResponse(call: Call<BooksData?>, response: Response<BooksData?>) {

                if(response.isSuccessful){

                    val responseBody = response?.body()
                    val books = responseBody?.results?.books

                    if(books != null){

                        appBooks = arrayListOf()

                        for (book in books){

                            var appBook = AppBook()

                            appBook.bookImage        = book.bookImage
                            appBook.bookImageWidth   = book.bookImageWidth
                            appBook.bookImageHeight  = book.bookImageHeight
                            appBook.title            = book.title
                            appBook.author           = book.author
                            appBook.rank             = book.rank
                            appBook.description      = book.description
                            appBook.publisher        = book.publisher
                            appBook.primaryIsbn13    = book.primaryIsbn13

                            appBooks.add(appBook)

                        }



                    }

                }else{

                    Log.d("MainActivity", "Failure: "+ response.message().toString())

                }

            }

            override fun onFailure(call: Call<BooksData?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: "+ t.message)
            }
        })

    }

}