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
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager
import kotlinx.android.synthetic.main.fragment_drag_and_drop_grid.*

class DragAndDropGridFragment: Fragment() {
    private val adapter = DragAndDropGridAdapter(8)
    private lateinit var dragDropManager: RecyclerViewDragDropManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_drag_and_drop_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dragDropManager = RecyclerViewDragDropManager()
        dragDropManager.setInitiateOnLongPress(false)
        dragDropManager.setInitiateOnMove(true)
        dragDropManager.setInitiateOnTouch(true)
        dragDropManager.isCheckCanDropEnabled = true
        dragDropManager.setLongPressTimeout(350)
        dragDropManager.dragStartItemAnimationDuration = 250
        dragDropManager.draggingItemAlpha = 0.8f
        dragDropManager.draggingItemScale = 1.3f
        dragDropManager.draggingItemRotation = 15.0f
        dragDropManager.itemMoveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_SWAP
        dragDropManager.attachRecyclerView(rv_drag_and_drop_grid)

        rv_drag_and_drop_grid.layoutManager = GridLayoutManager(requireContext(), 2)
        rv_drag_and_drop_grid.adapter = dragDropManager.createWrappedAdapter(adapter)
        rv_drag_and_drop_grid.itemAnimator = DraggableItemAnimator()

//        run()
    }

    override fun onPause() {
        dragDropManager.cancelDrag()
        super.onPause()
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