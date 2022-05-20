package com.enterprise.books.models

import com.google.gson.annotations.SerializedName

class AppBook (

    var bookImage          : String?             = null,
    var bookImageWidth     : Int?                = null,
    var bookImageHeight    : Int?                = null,
    var title              : String?             = null,
    var author             : String?             = null,
    var rank               : Int?                = null,
    var description        : String?             = null,
    var publisher          : String?             = null,
    var primaryIsbn13      : String?             = null,

)