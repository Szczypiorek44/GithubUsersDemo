package com.example.presentation.ui

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.hasScrolledToBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}