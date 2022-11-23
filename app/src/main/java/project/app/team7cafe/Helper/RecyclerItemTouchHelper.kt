package project.app.team7cafe.Helper

import android.graphics.Canvas
import android.view.View
import project.app.team7cafe.Interface.RecyclerItemTouchHelperListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import project.app.team7cafe.Model.Order
import project.app.team7cafe.ViewHolder.CartViewHolder
import java.util.ArrayList

class RecyclerItemTouchHelper(
    dragDirs: Int,
    swipeDirs: Int,
    listener: RecyclerItemTouchHelperListener?
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    private val listener: RecyclerItemTouchHelperListener?

    init {
        this.listener = listener
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener?.onSwipped(viewHolder, direction, viewHolder.adapterPosition)
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val front: View = (viewHolder as CartViewHolder).view_front
        getDefaultUIUtil().clearView(front)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val front: View = (viewHolder as CartViewHolder).view_front
        getDefaultUIUtil().onDraw(c, recyclerView, front, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            val front: View = (viewHolder as CartViewHolder).view_front
            getDefaultUIUtil().onSelected(front)
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val front: View = (viewHolder as CartViewHolder).view_front
        getDefaultUIUtil().onDrawOver(
            c,
            recyclerView,
            front,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }
}