package no.amund.bookstore.bookList

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.book_list_item.view.*
import no.amund.bookstore.R
import no.amund.bookstore.model.Book

/**
 * Created by Amund Bogetvedt on 12/03/2020.
 */

class BookListPagedAdapter(private val delegate: BookAdapterInterface, val context: Context?) :
    PagedListAdapter<Book, BookListPagedAdapter.BookViewHolder>(BookDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false)
        return BookViewHolder(inflatedView, delegate)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(getItem(position))
        }
    }

    fun deleteItem(position: Int) {
        getItem(position)?.let { book ->
            delegate.removeBook(book)
            showUndoSnackbar(book)
        }
    }

    private fun showUndoSnackbar(book: Book) {
        val view = (context as Activity).findViewById(R.id.nav_host_fragment) as View
        val snackbar = Snackbar.make(view, "${book.title} removed", Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") { undoDelete(book) }
        snackbar.show()
    }

    private fun undoDelete(book: Book) {
        delegate.addBook(book)
    }

    class BookViewHolder(val v: View, private val delegate: BookAdapterInterface) :
        RecyclerView.ViewHolder(v) {
        fun bind(book: Book?) {
            book?.let {
                v.setOnClickListener {
                    delegate.openBookDetails(book)
                }
                v.itemTitle.text = book.title
                v.itemAuthor.text = book.author
                v.favoriteButton.isSelected = book.isFavorite
                v.favoriteButton.setOnClickListener {
                    val updatedBook = Book(
                        id = book.id,
                        title = book.title,
                        author = book.author,
                        description = book.description,
                        isFavorite = !book.isFavorite
                    )
                    delegate.updateBooklist(updatedBook)
                }
            }

        }
    }

    class BookDiffItemCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

    }

    interface BookAdapterInterface {
        fun updateBooklist(book: Book)
        fun addBook(book: Book)
        fun removeBook(book: Book)
        fun openBookDetails(book: Book)
    }

}