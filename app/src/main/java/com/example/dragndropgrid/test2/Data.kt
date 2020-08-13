package com.example.dragndropgrid.test2

class Data(val id: Long, val viewType: Int, text: String) {
    val text: String = "$id - $text"
    override fun toString(): String  = text
}