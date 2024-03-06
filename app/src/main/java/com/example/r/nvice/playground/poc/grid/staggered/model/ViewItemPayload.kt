package com.example.r.nvice.playground.poc.grid.staggered.model

sealed interface ViewItemPayload {
    data class Playable(
        val isPlaying: Boolean
    ): ViewItemPayload
}