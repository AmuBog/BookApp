package no.amund.bookstore.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Amund Bogetvedt on 2020-01-31.
 */

@Parcelize
@Entity(tableName = "book")
data class Book (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String?,
    val author: String?,
    val description: String?,
    val isFavorite: Boolean
) : Parcelable