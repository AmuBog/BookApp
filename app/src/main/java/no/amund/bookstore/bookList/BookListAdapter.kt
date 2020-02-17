package no.amund.bookstore.bookList

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.book_list_item.view.*
import no.amund.bookstore.R
import no.amund.bookstore.model.Book

/**
 * Created by Amund Bogetvedt on 2019-12-05.
 */

class BookListAdapter(private val items: MutableList<Book>, private val delegate: BookAdapterInterface, val context: Context?) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false)
        return BookViewHolder(inflatedView, delegate)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun replaceList(list: List<Book>?) {
        list?.let {
            if (items.isNotEmpty()) {
                val diffResult = DiffUtil.calculateDiff(BookDiffCallback(items, it))
                items.clear()
                items.addAll(it)
                diffResult.dispatchUpdatesTo(this)
            } else {
                this.items.clear()
                this.items.addAll(list)
                notifyDataSetChanged()
            }
        }
    }

    fun deleteItem(position: Int) {
        delegate.removeBook(items[position])
        showUndoSnackbar(items[position])
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
        fun bind(book: Book) {
            Log.e(javaClass.canonicalName, book.id.toString())
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

    class BookDiffCallback(private val oldBooks: List<Book>, private val newBooks: List<Book>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldBooks.size
        }

        override fun getNewListSize(): Int {
            return newBooks.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldBooks[oldItemPosition].id == newBooks[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldBooks[oldItemPosition] == newBooks[newItemPosition]
        }

    }

    interface BookAdapterInterface {
        fun updateBooklist(book: Book)
        fun addBook(book: Book)
        fun removeBook(book: Book)
        fun openBookDetails(book: Book)
    }
}