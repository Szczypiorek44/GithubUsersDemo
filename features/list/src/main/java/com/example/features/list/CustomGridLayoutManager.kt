package com.example.features.list

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomGridLayoutManager(
    context: Context,
    private val numRows: Int,
    private val numColumns: Int
) : GridLayoutManager(context, numColumns, RecyclerView.HORIZONTAL, false) {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.itemCount == 0) {
            removeAndRecycleAllViews(recycler)
            return
        }

        detachAndScrapAttachedViews(recycler)

        val itemWidth = width / numColumns
        val itemHeight = height / numRows
        val totalItemWidth = numColumns * itemWidth
        val spacing = (width - totalItemWidth) / (numColumns - 1)

        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)

            addView(view)

            val row = i % numRows
            val col = i / numRows
            var left = col * itemWidth
            val top = row * itemHeight
            var right = left + itemWidth
            val bottom = top + itemHeight

            measureChildWithMargins(view, 0, 0)
            layoutDecorated(view, left, top, right, bottom)
        }
    }
}