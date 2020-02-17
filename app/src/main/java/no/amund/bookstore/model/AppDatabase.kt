package no.amund.bookstore.model

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Amund Bogetvedt on 2020-02-07.
 */

@Database(entities = arrayOf(Book::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}