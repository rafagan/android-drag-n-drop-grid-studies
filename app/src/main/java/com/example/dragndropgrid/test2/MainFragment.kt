package com.example.dragndropgrid.test2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dragndropgrid.R
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager


class MainFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DraggableGridAdapter
    private lateinit var wrappedAdapter: RecyclerView.Adapter<*>
    private lateinit var dragDropManager: RecyclerViewDragDropManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_test2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = getView()!!.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.itemAnimator = DraggableItemAnimator()

        dragDropManager = RecyclerViewDragDropManager()
        dragDropManager.setInitiateOnLongPress(true)
        dragDropManager.setInitiateOnMove(false)
        dragDropManager.setLongPressTimeout(350)
        dragDropManager.dragStartItemAnimationDuration = 250
        dragDropManager.draggingItemAlpha = 0.8f
        dragDropManager.draggingItemScale = 1.3f
        dragDropManager.draggingItemRotation = 15.0f
        dragDropManager.itemMoveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_SWAP
        dragDropManager.attachRecyclerView(recyclerView)

        adapter = DraggableGridAdapter(DataProvider())
        adapter.moveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_SWAP
        wrappedAdapter = dragDropManager.createWrappedAdapter(adapter)
        recyclerView.adapter = wrappedAdapter
    }

    override fun onPause() {
        dragDropManager.cancelDrag()
        super.onPause()
    }
}