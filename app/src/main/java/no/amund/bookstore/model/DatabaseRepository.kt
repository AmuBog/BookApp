package no.amund.bookstore.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
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

    private var appDatabase: AppDatabase? = AppDatabase.getAppDataBase(context!!)

    fun getAllBooks(): LiveData<List<Book>> {
        return appDatabase?.bookDao()?.getAll()!!
    }

    fun getFavouriteBooks(): LiveData<List<Book>> {
        return appDatabase?.bookDao()?.getFavouriteBooks()!!
    }

    fun getBooksPaged(): DataSource.Factory<Int, Book> {
        return appDatabase?.bookDao()?.getAllPaged()!!
    }

    fun getFavoriteBooksPaged(): DataSource.Factory<Int, Book> {
        return appDatabase?.bookDao()?.getFavouritesPaged()!!
    }

    fun addBook(title: String, author: String, description: String) {
        val book = Book(
            id = null,
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
                appDatabase?.bookDao()?.insertBook(book)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun updateBook(book: Book) {
        launch {
            try {
                Log.e(javaClass.canonicalName, book.isFavorite.toString())
                appDatabase?.bookDao()?.updateBook(book)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun deleteBook(book: Book) {
        launch {
            try {
                appDatabase?.bookDao()?.deleteBook(book)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}