package com.example.dragndropgrid

import androidx.recyclerview.widget.RecyclerView.ViewHolder


interface OnStartDragListener {
    fun onStartDrag(viewHolder: ViewHolder)
}