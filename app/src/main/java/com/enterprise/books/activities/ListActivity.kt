package com.enterprise.books.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.books.R
import com.enterprise.books.adapters.AppBookAdapter
import com.enterprise.books.databases.BookDatabase
import com.enterprise.books.models.AppBook
import kotlin.concurrent.thread

class ListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        thread {

            var appBooksList = BookDatabase.getDatabase(application).getBookDao().getAllAppBooks()

            runOnUiThread {

                if(appBooksList != null && appBooksList.isNotEmpty()){

                    val recyclerViewAppBooks: RecyclerView = findViewById(R.id.recyclerViewAppBooks)
                    recyclerViewAppBooks.adapter = AppBookAdapter(appBooksList as ArrayList<AppBook>, this)

                }

            }

        }

    }
}