package no.amund.bookstore.model

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Amund Bogetvedt on 2020-02-07.
 */

@Dao
interface BookDao {
    @Query("SELECT * FROM Book")
    fun getAll(): LiveData<List<Book>>

    @Query("SELECT * FROM Book WHERE isFavorite=1")
    fun getFavouriteBooks(): LiveData<List<Book>>

    @Insert
    suspend fun insertBook(vararg book: Book)

    @Update
     suspend fun updateBook(vararg book: Book)

    @Delete
     suspend fun deleteBook(vararg book: Book)
}