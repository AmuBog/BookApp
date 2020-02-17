package no.amund.bookstore.addBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_book.*
import kotlinx.android.synthetic.main.fragment_add_book.view.*
import no.amund.bookstore.R
import no.amund.bookstore.model.DatabaseRepository

class AddBookFragment : Fragment() {

    private val databaseRepository: DatabaseRepository by lazy {
        DatabaseRepository(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_book, container, false)

        view.buttonSubmit.setOnClickListener { addBook() }

        return view
    }

    private fun addBook() {
        if (editTitle.text.isNotEmpty()) {

            databaseRepository.addBook(
                editTitle.text.toString(),
                editAuthor.text.toString(),
                editDescription.text.toString()
            )

            findNavController().navigateUp()
        }
    }
}
