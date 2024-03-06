package com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.databinding.ViewHolderItem16x9Binding
import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItem

open class Item16x9ViewHolder(
    protected val binding: ViewHolderItem16x9Binding
) : ViewHolder(binding.root), ItemViewHolder {
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
        with(binding) {
            tvPosition.text = "$position"
        }
    }
}