package com.example.dragndropgrid.test2

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dragndropgrid.R
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager


class MainFragment : Fragment() {
    lateinit var dataProvider: AbstractDataProvider

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: DraggableGridExampleAdapter
    private lateinit var mWrappedAdapter: RecyclerView.Adapter<*>
    private lateinit var mRecyclerViewDragDropManager: RecyclerViewDragDropManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_test2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retainInstance = true
        dataProvider = ExampleDataProvider()

        //noinspection ConstantConditions
        mRecyclerView = getView()!!.findViewById(R.id.recycler_view)
        mLayoutManager = GridLayoutManager(requireContext(), 2)

        // drag & drop manager

        // drag & drop manager
        mRecyclerViewDragDropManager = RecyclerViewDragDropManager()
//        mRecyclerViewDragDropManager.setDraggingItemShadowDrawable(
//            ContextCompat.getDrawable(
//                requireContext(),
//                R.drawable.material_shadow_z3
//            ) as NinePatchDrawable?
//        )
        // Start dragging after long press
        // Start dragging after long press
        mRecyclerViewDragDropManager.setInitiateOnLongPress(true)
        mRecyclerViewDragDropManager.setInitiateOnMove(false)
        mRecyclerViewDragDropManager.setLongPressTimeout(750)

        // setup dragging item effects (NOTE: DraggableItemAnimator is required)

        // setup dragging item effects (NOTE: DraggableItemAnimator is required)
        mRecyclerViewDragDropManager.setDragStartItemAnimationDuration(250)
        mRecyclerViewDragDropManager.setDraggingItemAlpha(0.8f)
        mRecyclerViewDragDropManager.setDraggingItemScale(1.3f)
        mRecyclerViewDragDropManager.setDraggingItemRotation(15.0f)

        //adapter

        //adapter
        val myItemAdapter = DraggableGridExampleAdapter(dataProvider)
        mAdapter = myItemAdapter

        mWrappedAdapter =
            mRecyclerViewDragDropManager.createWrappedAdapter(myItemAdapter) // wrap for dragging


        val animator: GeneralItemAnimator =
            DraggableItemAnimator() // DraggableItemAnimator is required to make item animations properly.


        mRecyclerView.setLayoutManager(mLayoutManager)
        mRecyclerView.setAdapter(mWrappedAdapter) // requires *wrapped* adapter

        mRecyclerView.setItemAnimator(animator)

        // additional decorations
        //noinspection StatementWithEmptyBody

        // additional decorations
//        if (supportsViewElevation()) {
//            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
//        } else {
//            mRecyclerView.addItemDecoration(
//                ItemShadowDecorator(
//                    (ContextCompat.getDrawable(
//                        requireContext(),
//                        R.drawable.material_shadow_z1
//                    ) as NinePatchDrawable?)!!
//                )
//            )
//        }

        mRecyclerViewDragDropManager.attachRecyclerView(mRecyclerView)

        // for debugging
//        animator.setDebug(true);
//        animator.setMoveDuration(2000);

        updateItemMoveMode(
            true
        )
    }

    override fun onPause() {
        mRecyclerViewDragDropManager.cancelDrag()
        super.onPause()
    }


    private fun updateItemMoveMode(swapMode: Boolean) {
        val mode =
            if (swapMode) RecyclerViewDragDropManager.ITEM_MOVE_MODE_SWAP else RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT
        mRecyclerViewDragDropManager.itemMoveMode = mode
        mAdapter.setItemMoveMode(mode)
    }

    private fun supportsViewElevation(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }
}