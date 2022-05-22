package com.enterprise.books.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.books.R
import com.enterprise.books.databases.BookDatabase
import com.enterprise.books.models.AppBook
import kotlin.concurrent.thread


class AppBookAdapter(val appBooksList: ArrayList<AppBook>, val context: Context) :
    RecyclerView.Adapter<AppBookAdapter.AppBookViewHolder>() {

    class AppBookViewHolder(itemView: View, appBooksList: ArrayList<AppBook>, context: Context) : RecyclerView.ViewHolder(itemView) {

        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        private val imageViewAppBook: ImageView = itemView.findViewById(R.id.imageViewAppBook)

        fun bind(appBook: AppBook, context: Context) {

            textViewTitle.text = appBook.title
            textViewAuthor.text = appBook.author

            thread {


                val element =  BookDatabase.getDatabase(context).getSmallImageDao().getSmallImage(appBook.primaryIsbn13)

                Handler(Looper.getMainLooper()).post {
                    imageViewAppBook.setImageBitmap(element?.smallImage)
                }

            }


        }

        init{

            itemView.setOnClickListener{

                var selectedAppBook = appBooksList[adapterPosition]

                //val appIntent = Intent(context, CityDetailActivity::class.java)
                //appIntent.putExtra(WeatherForecastConstants.SelectedNearCityKey, selectedNearCity)

                //context.startActivity(appIntent)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppBookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.app_book, parent, false)

        return AppBookViewHolder(view, appBooksList, context)
    }

    override fun getItemCount(): Int {

        return appBooksList.size

    }

    override fun onBindViewHolder(holder: AppBookViewHolder, position: Int) {

        appBooksList[position]?.let { holder.bind(it, context) }

    }
}