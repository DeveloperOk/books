package com.enterprise.books.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enterprise.books.models.SmallImage

@Dao
interface SmallImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSmallImage(smallImage: SmallImage)

    @Query("SELECT * FROM small_image_table WHERE primaryIsbn13 =:primaryIsbn13" )
    fun getSmallImage(primaryIsbn13: String): SmallImage

}