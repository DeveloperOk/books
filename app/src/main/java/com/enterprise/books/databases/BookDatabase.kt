package com.enterprise.books.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enterprise.books.converters.Converters
import com.enterprise.books.interfaces.BookDao
import com.enterprise.books.interfaces.SmallImageDao
import com.enterprise.books.models.AppBook
import com.enterprise.books.models.SmallImage

@Database(entities = [AppBook::class, SmallImage::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BookDatabase: RoomDatabase() {

    abstract fun getBookDao(): BookDao
    abstract fun getSmallImageDao(): SmallImageDao

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