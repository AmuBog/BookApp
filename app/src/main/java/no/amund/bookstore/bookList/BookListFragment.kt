package no.amund.bookstore.bookList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_book_list.*
import no.amund.bookstore.R
import no.amund.bookstore.model.Book

class BookListFragment : Fragment(), BookListAdapter.BookAdapterInterface {

    private lateinit var bookAdapter: BookListAdapter
    private var isFavoriteList: Boolean = false
    private lateinit var bookListViewModel: BookListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFavoriteList = this.arguments?.getBoolean(ARG_FAVOURITES, false) ?: false

        bookListViewModel = ViewModelProvider(this).get(BookListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bookAdapter = BookListAdapter(mutableListOf(), this, context)

        startObserving(if (isFavoriteList) bookListViewModel.getFavoriteBooks() else bookListViewModel.getAllBooks())

        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewBooks.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = bookAdapter
            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(bookAdapter))
            itemTouchHelper.attachToRecyclerView(this)
        }

    }

    private fun startObserving(liveData: LiveData<List<Book>>) {
        liveData.observe(this, Observer {
            if (::bookAdapter.isInitialized) {
                bookAdapter.replaceList(it)
            }
        })
    }

    override fun updateBooklist(book: Book) {
        bookListViewModel.updateBook(book)
    }

    override fun addBook(book: Book) {
        bookListViewModel.insertBook(book)
    }

    override fun removeBook(book: Book) {
        bookListViewModel.deleteBook(book)
    }

    override fun openBookDetails(book: Book) {
        val bundle = bundleOf("book" to book)
        findNavController().navigate(R.id.action_bookListFragment_to_updateBookFragment, bundle)
    }

    companion object {
        const val ARG_FAVOURITES = "favorites"
    }
}
