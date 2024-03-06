package com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder

import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItem

sealed interface ItemViewHolder {
    fun bind(item: ViewItem, position: Int)
}

interface PlayableViewHolder {
    fun setPlaying(isPlaying: Boolean)
}