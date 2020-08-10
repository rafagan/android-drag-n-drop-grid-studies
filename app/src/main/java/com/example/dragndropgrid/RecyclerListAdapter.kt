package com.example.dragndropgrid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class RecyclerListAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    private val mItems: MutableList<String> =
        ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView!!.text = mItems[position]
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
}