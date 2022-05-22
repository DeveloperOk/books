package com.enterprise.books

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.enterprise.books.constants.ImageConstants
import com.enterprise.books.constants.NytimesApiConstants
import com.enterprise.books.databases.BookDatabase
import com.enterprise.books.interfaces.NytimesApi
import com.enterprise.books.models.AppBook
import com.enterprise.books.models.BooksData
import com.enterprise.books.models.SmallImage
import com.squareup.picasso.Picasso
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread{

            getBooks()

            readBooks()

            getSmallImage();

        }

    }

    private fun getSmallImage() {

        val smallImageDao = BookDatabase.getDatabase(application).getSmallImageDao()
        val element = smallImageDao.getSmallImage("9780316499378")

        var imageView: ImageView = findViewById(R.id.imageView)

        runOnUiThread {
            imageView.setImageBitmap(element.smallImage)
        }
    }


    private fun readBooks() {
        val bookDao = BookDatabase.getDatabase(application).getBookDao()

        val list = bookDao.getAllAppBooks()
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

                        val bookDao = BookDatabase.getDatabase(application).getBookDao()
                        val smallImageDao = BookDatabase.getDatabase(application).getSmallImageDao()

                        for (book in books){



                            if(book.primaryIsbn13 != null){

                                var appBook = AppBook()
                                var smallImage = SmallImage()

                                appBook.primaryIsbn13    = book.primaryIsbn13!!
                                smallImage.primaryIsbn13    = book.primaryIsbn13!!


                                appBook.bookImage        = book.bookImage
                                appBook.bookImageWidth   = book.bookImageWidth
                                appBook.bookImageHeight  = book.bookImageHeight
                                appBook.title            = book.title
                                appBook.author           = book.author
                                appBook.rank             = book.rank
                                appBook.description      = book.description
                                appBook.publisher        = book.publisher


                                thread{
                                    bookDao.addAppBook(appBook)

                                    val bitmap: Bitmap = Picasso.get().load(appBook.bookImage).resize(ImageConstants.SmallImageWidth,0).get()
                                    smallImage.smallImage = bitmap
                                    smallImageDao.addSmallImage(smallImage)

                                }

                            }
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