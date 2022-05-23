package com.enterprise.books.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enterprise.books.models.BigImage
import com.enterprise.books.models.SmallImage


@Dao
interface BigImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBigImage(bigImage: BigImage)

    @Query("SELECT * FROM big_image_table WHERE primaryIsbn13 =:primaryIsbn13" )
    fun getBigImage(primaryIsbn13: String): BigImage?

}