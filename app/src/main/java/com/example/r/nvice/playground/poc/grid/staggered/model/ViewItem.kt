package com.example.r.nvice.playground.poc.grid.staggered.model

data class ViewItem(
    val id: Int,
    val type: ViewItemType,
    val isPlaying: Boolean = false
)

val ViewItem.isPlayable
    get() = when (type) {
        ViewItemType.ITEM_3X4_PLAYABLE,
        ViewItemType.ITEM_1X1_PLAYABLE,
        ViewItemType.ITEM_16X9_PLAYABLE -> true

        else -> false
    }