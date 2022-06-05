package ercanduman.android.audiorecorder.ui.main.recording.list.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteTouchHelper(
    private val listener: OnRecordSwipedListener
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        listener.onRecordSwiped(position)
    }

    interface OnRecordSwipedListener {
        fun onRecordSwiped(position: Int)
    }
}
