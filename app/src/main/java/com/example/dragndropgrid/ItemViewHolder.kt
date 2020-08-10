package com.example.dragndropgrid

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
    val textView: TextView? = itemView as? TextView

}