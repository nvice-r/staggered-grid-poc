package com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.databinding.ViewHolderItem3x4Binding
import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItem

open class Item3x4ViewHolder(
    protected val binding: ViewHolderItem3x4Binding
) : ViewHolder(binding.root), ItemViewHolder {
    constructor(
        parent: ViewGroup,
        attachToParent: Boolean = false
    ) : this(
        ViewHolderItem3x4Binding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            attachToParent
        )
    )

    override fun bind(item: ViewItem, position: Int) {
        with(binding) {
            tvPosition.text = "$position"
        }
    }
}