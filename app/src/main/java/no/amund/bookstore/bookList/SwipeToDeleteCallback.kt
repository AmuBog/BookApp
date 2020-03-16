package no.amund.bookstore.bookList

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import no.amund.bookstore.R

/**
 * Created by Amund Bogetvedt on 2019-12-06.
 */

class SwipeToDeleteCallback(private val mAdapter: BookListPagedAdapter) :
    ItemTouchHelper.SimpleCallback(0, (ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) ) {

    private var icon: Drawable = ContextCompat.getDrawable(mAdapter.context!!,
        R.drawable.ic_delete_white
    )!!
    private var background: ColorDrawable = ColorDrawable(Color.RED)

    override fun onMove( recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        mAdapter.deleteItem(position)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20

        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        when {
            dX > 0 -> {
                val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
                val iconRight = itemView.left + iconMargin

                if(dX>=itemView.left+iconMargin+icon.intrinsicWidth)
                    icon.setBounds(iconRight, iconTop, iconLeft, iconBottom)

                background.setBounds(itemView.left,
                    itemView.top,
                    itemView.left + dX.toInt() - backgroundCornerOffset,
                    itemView.bottom
                )
            }
            dX < 0 -> {
                val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                background.setBounds(
                    itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
            }
            else -> {
                icon.setBounds(0,0,0,0)
                background.setBounds(0,0,0,0)
            }
        }
        background.draw(c)
        icon.draw(c)
    }

}