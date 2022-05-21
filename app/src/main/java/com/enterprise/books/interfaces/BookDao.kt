package com.enterprise.books.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enterprise.books.models.AppBook

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAppBook(appBook: AppBook)

    @Query("SELECT * FROM book_table" )
    fun getAllAppBooks(): List<AppBook>

}