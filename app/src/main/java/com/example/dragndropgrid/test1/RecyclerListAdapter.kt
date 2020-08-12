package com.example.dragndropgrid.test1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dragndropgrid.R
import java.util.*


class RecyclerListAdapter : RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>(),
    ItemTouchHelperAdapter {
    var mDragStartListener: OnStartDragListener? = null

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ItemTouchHelperViewHolder {
        val textView: TextView = itemView.findViewById(R.id.text)
        val handleView: ImageView = itemView.findViewById(R.id.handle)

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    private val mItems: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_test1, parent, false)
        return ItemViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = mItems[position]

        holder.handleView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                mDragStartListener?.onStartDrag(holder)
            }

            return@setOnTouchListener false
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    companion object {
        private val STRINGS = arrayOf(
            "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
        )
    }

    init {
        mItems.addAll(listOf(*STRINGS))
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(mItems, fromPosition, toPosition)
//        notifyDataSetChanged()
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
    }
}