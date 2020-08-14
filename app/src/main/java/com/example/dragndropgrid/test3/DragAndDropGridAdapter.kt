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
import java.security.InvalidParameterException

class DragAndDropGridAdapter(private val size: Int) :
    RecyclerView.Adapter<DragAndDropGridAdapter.ViewHolder>(),
    DraggableItemAdapter<DragAndDropGridAdapter.ViewHolder>
{
    class ViewHolder(v: View) : AbstractDraggableItemViewHolder(v) {
        val viewGroup: FrameLayout = v.findViewById(R.id.vg_drag)
        val textView: TextView = v.findViewById(R.id.txt_drag)
        val dragView: ImageView = v.findViewById(R.id.img_drag)
    }

    enum class CellType {
        BLANK, FILL, ADD
    }

    class Cell(val index: Int) {
        var type = CellType.BLANK
        var content = ""

        fun clear(): Cell {
            type = CellType.BLANK
            content = ""
            return this
        }

        fun set(text: String): Cell {
            content = text
            type = CellType.FILL
            return this
        }

        fun setAdd(): Cell {
            type = CellType.ADD
            content = "+"
            return this
        }

        fun swap(cell: Cell) {
            val tmpType = type
            val tmpContent = content
            type = cell.type
            content = cell.content
            cell.type = tmpType
            cell.content = tmpContent
        }
    }

    private var count = 1
    private val items: Array<Cell>
    val length: Int
        get() = count - 1

    init {
        if(size % 2 != 0) throw InvalidParameterException("Size must be even")
        items = Array(size) { i ->
            val cell = Cell(i)
            cell.type = if(i == 0) CellType.ADD else CellType.BLANK
            cell.content = if(i == 0) "+" else ""
            cell
        }
    }

    /// Array to grid
    private fun atog(index: Int, size: Int): Int {
        return index * 2 + (if (index < size / 2) 0 else -size + 1)
    }

    /// Grid to array
    private fun gtoa(index: Int, size: Int): Int {
        return if(index % 2 == 0) index / 2 else index + (size / 2 - 1) - (index / 2)
    }

    fun add(text: String) {
        val item = items.find { it.type == CellType.ADD }
        item?.apply {
            if(count != size) {
                val addIndex = atog(count, size)
                items[addIndex].setAdd()
            }

            item.set(text)
            ++count
            notifyDataSetChanged()
        }
    }

    fun remove(index: Int) {
        for(i in index until length) {
            val current = atog(i, size)
            if(i == length - 1 && length == size) {
                items[current].setAdd()
                continue
            }

            val next = atog(i + 1, size)
            items[current].swap(items[next])
            if(i == length - 1) items[next].clear()
        }

        --count
        notifyDataSetChanged()
    }

    /// RecyclerView.Adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.cell_drag_and_drop_grid, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].content
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