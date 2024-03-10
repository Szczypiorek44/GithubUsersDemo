package com.example.features.list;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CustomSnapHelper extends LinearSnapHelper {

    private final int itemsPerGroup;

    public CustomSnapHelper(int itemsPerGroup) {
        this.itemsPerGroup = itemsPerGroup;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return RecyclerView.NO_POSITION;
        }

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

        int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

        if (firstVisibleItem == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        int centerPosition = (firstVisibleItem + lastVisibleItem) / 2;
        int targetPosition = centerPosition - centerPosition % itemsPerGroup;

        if (targetPosition < 0) {
            targetPosition = 0;
        }

        return targetPosition;
    }
}