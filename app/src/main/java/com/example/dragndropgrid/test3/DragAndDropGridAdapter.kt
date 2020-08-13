package com.example.dragndropgrid.test3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dragndropgrid.R
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder

class DragAndDropGridAdapter(private val size: Int) :
    RecyclerView.Adapter<DragAndDropGridAdapter.ViewHolder>(),
    DraggableItemAdapter<DragAndDropGridAdapter.ViewHolder>
{
    class ViewHolder(v: View) : AbstractDraggableItemViewHolder(v) {
        val viewGroup: FrameLayout = v.findViewById(R.id.vg_drag)
        val textView: TextView = v.findViewById(R.id.txt_drag)
        val dragView: ImageView = v.findViewById(R.id.img_drag)
    }

    /// RecyclerView.Adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.cell_drag_and_drop_grid, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = (position + 1).toString()
    }

    /// DraggableItemAdapter

    override fun onGetItemDraggableRange(holder: ViewHolder, position: Int): ItemDraggableRange? {
        TODO("Not yet implemented")
    }

    override fun onCheckCanStartDrag(holder: ViewHolder, position: Int, x: Int, y: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onItemDragStarted(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
        TODO("Not yet implemented")
    }

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        TODO("Not yet implemented")
    }
}