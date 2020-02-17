package no.amund.bookstore


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_update_book.*
import kotlinx.android.synthetic.main.fragment_update_book.view.*
import no.amund.bookstore.model.Book
import no.amund.bookstore.model.DatabaseRepository

class UpdateBookFragment : Fragment() {

    private val databaseRepository: DatabaseRepository by lazy {
        DatabaseRepository(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_update_book, container, false)
        val book = arguments?.get("book") as Book

        view.updateTitleField.setText(book.title)
        view.updateAuthorField.setText(book.author)
        view.updateDescriptionField.setText(book.description)
        view.buttonSubmit.setOnClickListener { updateBook() }
        return view
    }

    private fun updateBook() {
        databaseRepository.updateBook(
            Book(
                title = updateTitleField.toString(),
                author = updateAuthorField.toString(),
                description = updateDescriptionField.toString(),
                isFavorite = (arguments?.get("book") as Book).isFavorite
            )
        )
    }

}
