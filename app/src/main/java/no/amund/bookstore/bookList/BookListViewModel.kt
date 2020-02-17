package no.amund.bookstore.bookList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import no.amund.bookstore.model.Book
import no.amund.bookstore.model.DatabaseRepository

/**
 * Created by Amund Bogetvedt on 2020-02-14.
 */

class BookListViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: DatabaseRepository = DatabaseRepository(application)

    fun getAllBooks(): LiveData<List<Book>> {
        return repository.getAllBooks()
    }

    fun getFavoriteBooks(): LiveData<List<Book>> {
        return repository.getFavouriteBooks()
    }

    fun insertBook(book: Book) {
        repository.addBook(book)
    }

    fun updateBook(book: Book) {
        repository.updateBook(book)
    }

    fun deleteBook(book: Book) {
        repository.deleteBook(book)
    }

}