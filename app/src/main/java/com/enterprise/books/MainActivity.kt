package com.enterprise.books

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.enterprise.books.activities.ListActivity
import com.enterprise.books.constants.ImageConstants
import com.enterprise.books.constants.NytimesApiConstants
import com.enterprise.books.databases.BookDatabase
import com.enterprise.books.interfaces.NytimesApi
import com.enterprise.books.models.AppBook
import com.enterprise.books.models.BigImage
import com.enterprise.books.models.BooksData
import com.enterprise.books.models.SmallImage
import com.squareup.picasso.Picasso
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private var buttonDownloadBooks: Button? = null
    private var buttonListBooks: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BookDatabase.getDatabase(application)

        buttonDownloadBooks = findViewById(R.id.buttonDownloadBooks)
        buttonListBooks = findViewById(R.id.buttonListBooks)

        setButtonListeners()

    }

    private fun setButtonListeners() {

        buttonDownloadBooks?.setOnClickListener(View.OnClickListener {

            thread{

                getBooks()

            }

        })

        buttonListBooks?.setOnClickListener(View.OnClickListener {

            launchListActivity()

        })

    }

    private fun launchListActivity() {

        val appIntent = Intent(this, ListActivity::class.java)
        startActivity(appIntent)

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

                        for (book in books){

                            if(book.primaryIsbn13 != null){

                                var appBook = AppBook()
                                var smallImage = SmallImage()
                                var bigImage = BigImage()

                                appBook.primaryIsbn13       = book.primaryIsbn13!!
                                smallImage.primaryIsbn13    = book.primaryIsbn13!!
                                bigImage.primaryIsbn13      = book.primaryIsbn13!!

                                appBook.bookImage        = book.bookImage
                                appBook.bookImageWidth   = book.bookImageWidth
                                appBook.bookImageHeight  = book.bookImageHeight
                                appBook.title            = book.title
                                appBook.author           = book.author
                                appBook.rank             = book.rank
                                appBook.description      = book.description
                                appBook.publisher        = book.publisher


                                thread{
                                    BookDatabase.getDatabase(application).getBookDao().addAppBook(appBook)

                                    val bitmapSmall: Bitmap = Picasso.get().load(appBook.bookImage).resize(ImageConstants.SmallImageWidth,0).get()
                                    smallImage.smallImage = bitmapSmall
                                    BookDatabase.getDatabase(application).getSmallImageDao().addSmallImage(smallImage)

                                    val bitmapBig: Bitmap = Picasso.get().load(appBook.bookImage).resize(ImageConstants.BigImageWidth,0).get()
                                    bigImage.bigImage = bitmapBig
                                    BookDatabase.getDatabase(application).getBigImageDao().addBigImage(bigImage)

                                }

                            }
                        }

                        Thread.sleep(2000)
                        Toast.makeText(applicationContext, R.string.main_activity_books_downloaded_message, Toast.LENGTH_LONG).show()

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