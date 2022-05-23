package com.enterprise.books.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enterprise.books.models.AppBook
import com.enterprise.books.models.SmallImage

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAppBook(appBook: AppBook)

    @Query("SELECT * FROM book_table" )
    fun getAllAppBooks(): List<AppBook>

    @Query("SELECT * FROM book_table WHERE primaryIsbn13 =:primaryIsbn13" )
    fun getAppBook(primaryIsbn13: String): AppBook?

}