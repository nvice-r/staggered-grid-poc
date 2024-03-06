package com.example.r.nvice.playground.poc.grid.staggered.ui.widget.recyclerview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.r.nvice.playground.poc.grid.staggered.util.getGlobalVisibility
import timber.log.Timber
import kotlin.math.ceil

class OptimizedStaggeredGridRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(
            if (layout !is StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager(
                    1,
                    StaggeredGridLayoutManager.VERTICAL
                )
            } else layout
        )
    }

    fun findOptimalItemPosition(): Int? {
        return (layoutManager as? StaggeredGridLayoutManager)
            ?.findOptimalItemPosition(this)
    }

    private fun StaggeredGridLayoutManager.findOptimalItemPosition(
        viewport: View
    ): Int? {
        val viewportRect = Rect().also(viewport::getGlobalVisibleRect)

        val topExitingItemPositions = findFirstVisibleItemPositions(null).toList()
        Timber.i("topExitingItemPositions: $topExitingItemPositions")

        // finding a tallest item for sectoring criteria
        // from the top exiting items
        val criteriaItemVisibility = topExitingItemPositions.maxOf { position ->
            findViewByPosition(position)
                ?.getGlobalVisibility(viewportRect)
                ?: 0
        }
        Timber.i("criteriaItemVisibility: $criteriaItemVisibility")

        // finding candidate items
        val candidatePositions = findFirstCompletelyVisibleItemPositions(null)
            .filter { position ->
                position >= 0
            }
            .sorted()
            .toMutableList()

        if (candidatePositions.isEmpty()) {
            // no complete visible on the screen
            // then candidate items will be the last item of topExitingItemPositions
            // and its next one. (junction)
            val topExitingItemPosition = topExitingItemPositions.last()
            candidatePositions.addAll(
                listOf(
                    topExitingItemPosition,
                    topExitingItemPosition + 1
                )
            )
        }
        Timber.i("candidatePositions: $candidatePositions")

        val topExitingItemSector = ceil(
            ((100 - criteriaItemVisibility) * candidatePositions.size)
                    / 100.0
        ).toInt()
        Timber.i("topExitingItemSector: $topExitingItemSector")

        return candidatePositions.getOrElse(
            if (topExitingItemSector > 0) topExitingItemSector - 1 else topExitingItemSector
        ) {
            // in the worst case,
            // return last item of topExitingItemPositions
            topExitingItemPositions.lastOrNull()
        }?.also {
            Timber.i("optimalPosition: $it")
        }
    }
}