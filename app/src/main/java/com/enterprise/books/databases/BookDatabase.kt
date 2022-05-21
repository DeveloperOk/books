package com.enterprise.books.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enterprise.books.interfaces.BookDao
import com.enterprise.books.models.AppBook

@Database(entities = [AppBook::class], version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {

    abstract fun getBookDao(): BookDao

    companion object {

        private val databaseName = "book_database"

        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    databaseName)
                    .build()

                INSTANCE = instance

                return instance
            }

        }

    }


}