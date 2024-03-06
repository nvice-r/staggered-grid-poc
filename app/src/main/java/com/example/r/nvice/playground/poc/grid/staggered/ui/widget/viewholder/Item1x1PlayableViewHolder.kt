package com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.R
import com.example.r.nvice.playground.poc.grid.staggered.databinding.ViewHolderItem1x1Binding
import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItem

class Item1x1PlayableViewHolder(
    binding: ViewHolderItem1x1Binding
) : Item1x1ViewHolder(binding), PlayableViewHolder {
    constructor(
        parent: ViewGroup,
        attachToParent: Boolean = false
    ) : this(
        ViewHolderItem1x1Binding.inflate(
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