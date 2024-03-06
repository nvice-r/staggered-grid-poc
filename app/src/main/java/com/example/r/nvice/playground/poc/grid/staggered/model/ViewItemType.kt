package com.example.r.nvice.playground.poc.grid.staggered.model

import kotlin.random.Random

enum class ViewItemType(val value: Int) {
    ITEM_3X4(0),
    ITEM_1X1(1),
    ITEM_16X9(2),
    ITEM_3X4_PLAYABLE(3),
    ITEM_1X1_PLAYABLE(4),
    ITEM_16X9_PLAYABLE(5);

    companion object {
        infix fun from(value: Int): ViewItemType? = entries.firstOrNull { it.value == value }
        fun random(): ViewItemType = Random.nextInt(0, 6).let(::from) ?: ITEM_1X1
    }
}
