package com.example.dragndropgrid.test3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dragndropgrid.R
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder
import java.security.InvalidParameterException


class DragAndDropGridAdapter(private val size: Int, private val onAdd: () -> Unit) :
    RecyclerView.Adapter<DragAndDropGridAdapter.ViewHolder>(),
    DraggableItemAdapter<DragAndDropGridAdapter.ViewHolder>
{
    class ViewHolder(v: View, val onAdd: () -> Unit, val onRemove: (Int) -> Unit) : AbstractDraggableItemViewHolder(v) {
        val container: ViewGroup = v.findViewById(R.id.vg_drag)
        val textView: TextView = v.findViewById(R.id.txt_drag)
        val dragView: ImageView = v.findViewById(R.id.img_drag)
        val deleteButton: Button = v.findViewById(R.id.bt_delete)

        fun bind(cell: Cell) {
            textView.text = cell.content
            container.setOnClickListener {
                if(cell.type == CellType.ADD) onAdd()
            }

            if(cell.type == CellType.FILL) {
                dragView.visibility = View.VISIBLE
                deleteButton.visibility = View.VISIBLE
                deleteButton.setOnClickListener { onRemove(cell.index) }
            } else {
                dragView.visibility = View.INVISIBLE
                deleteButton.visibility = View.INVISIBLE
            }
        }
    }

    enum class CellType {
        BLANK, FILL, ADD
    }

    class Cell(val index: Int) {
        var type = CellType.BLANK
        var content = ""
        var id: Long = index.toLong()

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
            val tmpId = id
            type = cell.type
            content = cell.content
            id = cell.id
            cell.type = tmpType
            cell.content = tmpContent
            cell.id = tmpId
        }

        override fun toString(): String = id.toString()
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

//        items = Array(size) { i ->
//            val cell = Cell(i)
//            cell.set(i.toString())
//            cell
//        }

        setHasStableIds(true)
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
        for(i in gtoa(index, size) until length) {
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
        val vh = ViewHolder(v, onAdd, { index -> remove(index) })
        vh.container.layoutParams.height = parent.measuredHeight / (size / 2)
        return vh
    }

    override fun getItemCount(): Int = size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

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

    override fun getItemId(position: Int): Long = items[position].id

    /// DraggableItemAdapter

    override fun onGetItemDraggableRange(holder: ViewHolder, position: Int): ItemDraggableRange? {
        return null
    }

    override fun onCheckCanStartDrag(holder: ViewHolder, position: Int, x: Int, y: Int): Boolean {
        return items[position].type == CellType.FILL
    }

    override fun onItemDragStarted(position: Int) {
        notifyDataSetChanged()
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition == toPosition) return
        items[toPosition].swap(items[fromPosition])
        Log.d("xxx", items.joinToString { it.toString() })
    }

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
        return items[dropPosition].type == CellType.FILL
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        notifyDataSetChanged()
    }
}