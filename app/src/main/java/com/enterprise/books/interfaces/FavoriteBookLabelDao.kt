package com.enterprise.books.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enterprise.books.models.FavoriteBookLabel
import com.enterprise.books.models.SmallImage

@Dao
interface FavoriteBookLabelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteBookLabel(favoriteBookLabel: FavoriteBookLabel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteBookLabel(favoriteBookLabel: FavoriteBookLabel)

    @Query("SELECT * FROM favorite_book_label_table WHERE primaryIsbn13 =:primaryIsbn13" )
    fun getFavoriteBookLabel(primaryIsbn13: String): FavoriteBookLabel?

}