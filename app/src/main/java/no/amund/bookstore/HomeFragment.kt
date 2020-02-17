package no.amund.bookstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_home.view.*
import no.amund.bookstore.bookList.BookListFragment

class HomeFragment : Fragment() {

    private lateinit var bookListButton: Button
    private lateinit var addBookButton: Button
    private lateinit var favoriteBooksButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        bookListButton = view.buttonAllBooks.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_bookListFragment)
            }
        }
        addBookButton = view.buttonAddBook.apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addBookFragment)
            }
        }
        favoriteBooksButton = view.buttonFavoriteBooks.apply {
            setOnClickListener {
                val args = bundleOf(BookListFragment.ARG_FAVOURITES to true)
                findNavController().navigate(R.id.action_homeFragment_to_bookListFragment, args)
            }
        }

        return view
    }
}
