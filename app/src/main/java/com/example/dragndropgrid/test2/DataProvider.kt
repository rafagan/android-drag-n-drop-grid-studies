package com.example.dragndropgrid.test2

import java.util.*

class DataProvider {
    private val data: MutableList<Data?>

    val count: Int
        get() = data.size

    init {
        data = LinkedList()
        for (i in 0..1) {
            for (element in "ABCDEF") {
                if(data.size == 7) break

                val id = data.size.toLong()
                val viewType = 0
                val text = element.toString()
                data.add(Data(id, viewType, text))
            }
        }
    }

    fun getItem(index: Int): Data? {
        if (index < 0 || index >= count) throw IndexOutOfBoundsException("index = $index")
        return data[index]
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition == toPosition) return
        val item = data.removeAt(fromPosition)
        data.add(toPosition, item)
    }

    fun swapItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition == toPosition) return
        Collections.swap(data, toPosition, fromPosition)
    }

    fun removeItem(position: Int) {
        data.removeAt(position)
    }
}
