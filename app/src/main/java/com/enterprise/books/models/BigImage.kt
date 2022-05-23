package com.enterprise.books.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "big_image_table")
data class BigImage (

    @PrimaryKey
    var primaryIsbn13      : String             = "",

    var bigImage           : Bitmap?            = null

)