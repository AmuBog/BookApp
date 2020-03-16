package no.amund.bookstore.model

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
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

    @Query("SELECT * FROM Book")
    fun getAllPaged(): DataSource.Factory<Int, Book>

    @Query("SELECT * FROM Book WHERE isFavorite=1")
    fun getFavouritesPaged(): DataSource.Factory<Int, Book>

    @Insert
    suspend fun insertBook(vararg book: Book)

    @Update
     suspend fun updateBook(book: Book)

    @Delete
     suspend fun deleteBook(book: Book)
}