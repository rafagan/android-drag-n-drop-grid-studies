package com.example.dragndropgrid.test2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dragndropgrid.R
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder

class DraggableGridAdapter(private val provider: DataProvider) :
    RecyclerView.Adapter<DraggableGridAdapter.ViewHolder>(),
    DraggableItemAdapter<DraggableGridAdapter.ViewHolder>
{
    var moveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_SWAP

    init {
        setHasStableIds(true)
    }

    class ViewHolder(v: View) : AbstractDraggableItemViewHolder(v) {
        var container: FrameLayout = v.findViewById(R.id.container)
        var textView: TextView = v.findViewById(R.id.txt_cell)
    }

    override fun getItemId(position: Int): Long {
        return provider.getItem(position)!!.id
    }

    override fun getItemViewType(position: Int): Int {
        return provider.getItem(position)!!.viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.item_test2, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = provider.getItem(position)
        holder.textView.text = item!!.text

        // set background resource (target view ID: container)
        val dragState = holder.dragState
        if (dragState.isUpdated) {
            val bgResId: Int
            when {
                dragState.isActive -> {
                    bgResId = R.drawable.bg_item_dragging_active_state
                    holder.container.foreground.state = intArrayOf()
                }
                dragState.isDragging -> {
                    bgResId = R.drawable.bg_item_dragging_state
                }
                else -> {
                    bgResId = R.drawable.bg_item_normal_state
                }
            }
            holder.container.setBackgroundResource(bgResId)
        }
    }

    override fun getItemCount(): Int {
        return provider.count
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
        if (moveMode == RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT) {
            provider.moveItem(fromPosition, toPosition)
        } else {
            provider.swapItem(fromPosition, toPosition)
        }
    }

    override fun onCheckCanStartDrag(holder: ViewHolder, position: Int, x: Int, y: Int): Boolean {
        return true
    }

    override fun onGetItemDraggableRange(holder: ViewHolder, position: Int): ItemDraggableRange? {
        return null
    }

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
        return true
    }

    override fun onItemDragStarted(position: Int) {
        notifyDataSetChanged()
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        notifyDataSetChanged()
    }
}