package no.amund.bookstore.bookList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import no.amund.bookstore.model.Book
import no.amund.bookstore.model.DatabaseRepository

/**
 * Created by Amund Bogetvedt on 2020-02-14.
 */

class BookListViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: DatabaseRepository = DatabaseRepository(application)
    private var pagedBookLivedata: LiveData<PagedList<Book>>
    private var favoriteBookLivedata: LiveData<PagedList<Book>>

    init {
        val factory = repository.getBooksPaged()
        val favoriteFactory = repository.getFavoriteBooksPaged()
        val pageListConfig = PagedList.Config.Builder().apply {
            setInitialLoadSizeHint(24)
            setPageSize(6)
            setPrefetchDistance(4)
            setEnablePlaceholders(false)
        }.build()
        val pagedlistBuilder = LivePagedListBuilder(factory, pageListConfig)
        val favoritePagedlistBuilder = LivePagedListBuilder(favoriteFactory, pageListConfig)

        pagedBookLivedata = pagedlistBuilder.build()
        favoriteBookLivedata = favoritePagedlistBuilder.build()
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return repository.getAllBooks()
    }

    fun getFavoriteBooks(): LiveData<List<Book>> {
        return repository.getFavouriteBooks()
    }

    fun getBooksPaged() = pagedBookLivedata

    fun getFavoritesPaged() = favoriteBookLivedata

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