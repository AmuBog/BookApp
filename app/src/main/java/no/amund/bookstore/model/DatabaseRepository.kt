package no.amund.bookstore.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Amund Bogetvedt on 2020-02-10.
 */

class DatabaseRepository(context: Context?) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val DB_NAME = "book-database"
    private var appDatabase: AppDatabase

    init {
        appDatabase = Room.databaseBuilder(context!!.applicationContext, AppDatabase::class.java, DB_NAME).build()
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return appDatabase.bookDao().getAll()
    }

    fun getFavouriteBooks(): LiveData<List<Book>> {
        return appDatabase.bookDao().getFavouriteBooks()
    }

    fun addBook(title: String, author: String, description: String) {
        val book = Book(
            title = title,
            author = author,
            description = description,
            isFavorite = false
        )
        addBook(book)
    }

    fun addBook(book: Book) {
        launch {
            try {
                appDatabase.bookDao().insertBook(book)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun updateBook(book: Book) {
        launch {
            try {
                Log.e(javaClass.canonicalName, book.isFavorite.toString())
                appDatabase.bookDao().updateBook(book)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun deleteBook(book: Book) {
        launch {
            try {
                appDatabase.bookDao().deleteBook(book)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}