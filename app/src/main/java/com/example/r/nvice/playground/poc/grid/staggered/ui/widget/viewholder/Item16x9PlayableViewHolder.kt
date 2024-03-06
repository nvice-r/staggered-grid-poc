package com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.r.nvice.playground.poc.grid.staggered.databinding.ViewHolderItem16x9Binding
import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItem

class Item16x9PlayableViewHolder(
    binding: ViewHolderItem16x9Binding
) : Item16x9ViewHolder(binding), PlayableViewHolder {
    constructor(
        parent: ViewGroup,
        attachToParent: Boolean = false
    ) : this(
        ViewHolderItem16x9Binding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            attachToParent
        )
    )

    override fun bind(item: ViewItem, position: Int) {
        super.bind(item, position)
        binding.vCard.setCardBackgroundColor(
            if (item.isPlaying) Color.RED else Color.BLACK
        )
    }

    override fun setPlaying(isPlaying: Boolean) {
        binding.vCard.setCardBackgroundColor(
            if (isPlaying) Color.RED else Color.BLACK
        )
    }
}