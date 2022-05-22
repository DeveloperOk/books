package com.enterprise.books.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "small_image_table")
data class SmallImage (

    @PrimaryKey
    var primaryIsbn13      : String             = "",

    var smallImage        : Bitmap?            = null

)