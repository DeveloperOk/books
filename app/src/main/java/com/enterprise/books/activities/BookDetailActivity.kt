package com.enterprise.books.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.books.R
import com.enterprise.books.adapters.AppBookAdapter
import com.enterprise.books.constants.AppConstants
import com.enterprise.books.databases.BookDatabase
import com.enterprise.books.models.AppBook
import kotlin.concurrent.thread

class BookDetailActivity : AppCompatActivity() {

    private var selectedAppBookPrimaryIsbn13: String = ""
    private var imageViewBigImage: ImageView? = null
    private var textViewAppBookTitle: TextView? = null
    private var textViewAppBookAuthor: TextView? = null
    private var textViewAppBookRank: TextView? = null
    private var textViewAppBookPublisher: TextView? = null
    private var textViewAppBookDescription: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        title = getString(R.string.book_detail_activity_title)

        imageViewBigImage = findViewById(R.id.imageViewBigImage)
        textViewAppBookTitle = findViewById(R.id.textViewAppBookTitle)
        textViewAppBookAuthor = findViewById(R.id.textViewAppBookAuthor)
        textViewAppBookRank = findViewById(R.id.textViewAppBookRank)
        textViewAppBookPublisher = findViewById(R.id.textViewAppBookPublisher)
        textViewAppBookDescription = findViewById(R.id.textViewAppBookDescription)

        selectedAppBookPrimaryIsbn13 =
            intent.getStringExtra(AppConstants.SelectedAppBookPrimaryIsbn13).toString()

        updateUserInterface()

    }

    private fun updateUserInterface() {

        thread {

            var bigImage = BookDatabase.getDatabase(application).getBigImageDao().getBigImage(selectedAppBookPrimaryIsbn13)
            var selectedAppBook = BookDatabase.getDatabase(application).getBookDao().getAppBook(selectedAppBookPrimaryIsbn13)

            runOnUiThread {

                imageViewBigImage?.setImageBitmap(bigImage?.bigImage)
                textViewAppBookTitle?.text = getString(R.string.book_detail_activity_app_book_title) + AppConstants.Space + selectedAppBook?.title
                textViewAppBookAuthor?.text = getString(R.string.book_detail_activity_app_book_author) + AppConstants.Space + selectedAppBook?.author
                textViewAppBookRank?.text = getString(R.string.book_detail_activity_app_book_rank) + AppConstants.Space + selectedAppBook?.rank
                textViewAppBookPublisher?.text = getString(R.string.book_detail_activity_app_book_publisher) + AppConstants.Space + selectedAppBook?.publisher

                var inputStr = getString(R.string.book_detail_activity_app_book_description) + AppConstants.Space + selectedAppBook?.description
                var outputStr =  addLineBreaks(inputStr)
                textViewAppBookDescription?.text = outputStr


            }

        }

    }

    private fun addLineBreaks(inputStr: String): String {

        var space = " "
        var listOfWords = inputStr.split(space)
        var outputStr = ""
        var numberofCharactersOfLine = 50
        var multiplier = 1


        for(word in listOfWords){

            outputStr += space + word

            if(outputStr.length > numberofCharactersOfLine * multiplier){

                outputStr += System.lineSeparator()
                multiplier++
            }

        }

        return outputStr.trim()
    }
}