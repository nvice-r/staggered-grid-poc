package com.example.r.nvice.playground.poc.grid.staggered.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import kotlin.math.abs

fun RecyclerView.getItemVisibility(position: Int): Int? {
    val layoutManager = layoutManager ?: return null
    val viewportRect = Rect().also(::getGlobalVisibleRect)
    return layoutManager.findViewByPosition(position)
        ?.getGlobalVisibility(viewportRect)
}

fun View.getGlobalVisibility(viewportRect: Rect): Int {
    return Rect().also(::getGlobalVisibleRect).let { itemRect ->
        val diff = minOf(
            abs(viewportRect.top - itemRect.bottom).also {
                Timber.i("viewportRect.top - itemRect.bottom: $it")
            },
            abs(viewportRect.bottom - itemRect.top).also {
                Timber.i("viewportRect.bottom - itemRect.top: $it")
            }
        )
        Timber.i("smallest diff: $diff")
        ((diff * 100) / height)
            .coerceAtMost(100)
    }
}