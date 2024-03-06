package com.example.r.nvice.playground.poc.grid.staggered.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItem
import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItemType
import com.example.r.nvice.playground.poc.grid.staggered.model.isPlayable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {
    private val visibleItemRange
        get() = IntRange(
            firstVisibleItemPositions.first(),
            lastVisibleItemPositions.last()
        )

    private val visibleCompletelyItemRange
        get() = IntRange(
            firstCompletelyVisibleItemPositions.first(),
            lastCompletelyVisibleItemPositions.last()
        )

    private val _items = MutableLiveData<MutableList<ViewItem>>(mutableListOf())
    val items: LiveData<List<ViewItem>>
        get() = _items.map {
            it.toList()
        }

    private var _playingItemPosition: Int = -1
    val playingItemPosition: Int
        get() = _playingItemPosition

    private var firstVisibleItemPositions = intArrayOf()
    private var firstCompletelyVisibleItemPositions = intArrayOf()
    private var lastVisibleItemPositions = intArrayOf()
    private var lastCompletelyVisibleItemPositions = intArrayOf()

    fun refresh() {
        viewModelScope.launch {
            updateList {
                clear()
            }
            delay(100)
            randomItems()
        }
    }

    fun randomItems(count: Int = 20) {
        updateList {
            for (i in 0..count) {
                add(
                    ViewItem(
                        id = i,
                        type = ViewItemType.random(),
                        isPlaying = i == 0
                    )
                )
            }
        }
    }

    fun updateItemPositions(
        firstVisibleItemPositions: IntArray = this.firstVisibleItemPositions,
        firstCompletelyVisibleItemPositions: IntArray = this.firstCompletelyVisibleItemPositions,
        lastVisibleItemPositions: IntArray = this.lastVisibleItemPositions,
        lastCompletelyVisibleItemPositions: IntArray = this.lastCompletelyVisibleItemPositions
    ) {
        val lastVisibleItemPositionOffset = 0
        val items = _items.value
        if (!items.isNullOrEmpty()) {
            val lastVisibleItemPosition = lastVisibleItemPositions.maxOrNull()
            if (lastVisibleItemPosition != this.lastVisibleItemPositions.maxOrNull()) {
                val lastItemPosition = items.size - 1
                val shouldLoadMore =
                    lastVisibleItemPosition == (lastItemPosition - lastVisibleItemPositionOffset)
                if (shouldLoadMore) {
                    loadMoreItems()
                }
            }

            this.firstVisibleItemPositions = firstVisibleItemPositions
            this.firstCompletelyVisibleItemPositions = firstCompletelyVisibleItemPositions
            this.lastVisibleItemPositions = lastVisibleItemPositions
            this.lastCompletelyVisibleItemPositions = lastCompletelyVisibleItemPositions
        }
    }

    fun setPlayOnItem(position: Int) {
        Timber.i("setPlayOnItem: $position")
        Timber.i("visibleItemRange: $visibleItemRange")
        Timber.i("visibleCompletelyItemRange: $visibleCompletelyItemRange")
        if (position in visibleItemRange && position != _playingItemPosition) {
            _items.value?.let { items ->
                items.getOrNull(position)?.let { item ->
                    if (item.isPlayable) {
                        updateList {
                            set(
                                position,
                                item.copy(
                                    isPlaying = true
                                )
                            )
                            if (_playingItemPosition in visibleItemRange) {
                                items.getOrNull(_playingItemPosition)?.let { prevItem ->
                                    set(
                                        _playingItemPosition,
                                        prevItem.copy(
                                            isPlaying = false
                                        )
                                    )
                                }
                            }
                        }
                        _playingItemPosition = position
                    }
                }
            }
        }
    }

    fun updatePlayableVisibility(visibility: Int) {
        Timber.i("playingPosition: $_playingItemPosition")
        Timber.i("playingVisibility: $visibility")
        _items.value?.let { items ->
            updateList {
                items.getOrNull(_playingItemPosition)?.let { prevItem ->
                    set(
                        _playingItemPosition,
                        prevItem.copy(
                            isPlaying = visibility >= 75
                        )
                    )
                }
            }
        }
    }

    private fun loadMoreItems(count: Int = 20) {
        updateList {
            val nextId = last().id + 1
            for (i in 0..count) {
                add(
                    ViewItem(
                        id = nextId + i,
                        type = ViewItemType.random()
                    )
                )
            }
        }
    }

    private fun updateList(block: MutableList<ViewItem>.() -> Unit) {
        _items.value = _items.value?.apply {
            block(this)
        }
    }

    fun generateDummyData(): List<ViewItem> {
        val desiredOrder = listOf(
            ViewItemType.ITEM_16X9,
            ViewItemType.ITEM_1X1_PLAYABLE,
            ViewItemType.ITEM_3X4_PLAYABLE,
            ViewItemType.ITEM_1X1_PLAYABLE,
            ViewItemType.ITEM_3X4,
            ViewItemType.ITEM_16X9
        )

        return desiredOrder.mapIndexed { index, viewItemType ->
            ViewItem(
                id = index,
                type = viewItemType
            )
        }
    }
}