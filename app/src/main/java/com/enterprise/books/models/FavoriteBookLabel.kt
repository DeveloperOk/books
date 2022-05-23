package com.enterprise.books.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_book_label_table")
data class FavoriteBookLabel (

    @PrimaryKey
    var primaryIsbn13      : String             = "",

    var favorite        : Boolean           = false

)