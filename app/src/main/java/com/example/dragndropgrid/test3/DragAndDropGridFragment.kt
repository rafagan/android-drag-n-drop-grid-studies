package com.example.dragndropgrid.test3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dragndropgrid.R
import kotlinx.android.synthetic.main.fragment_drag_and_drop_grid.*

class DragAndDropGridFragment: Fragment() {
    private val adapter = DragAndDropGridAdapter(8)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_drag_and_drop_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_drag_and_drop_grid.layoutManager = GridLayoutManager(requireContext(), 2)
        rv_drag_and_drop_grid.adapter = adapter

        run()
    }

    var toggle = false

    private fun run() {
        Handler(Looper.getMainLooper()).postDelayed({
            if(adapter.length == 8) {
                toggle = true
            }
            if(adapter.length == 0) {
                toggle = false
            }

            if(!toggle) {
                adapter.add(adapter.length.toString())
            } else {
                adapter.remove(0)
            }

            run()
        }, 1000)
    }
}