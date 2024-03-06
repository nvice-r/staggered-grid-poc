package com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.r.nvice.playground.poc.grid.staggered.databinding.ViewHolderItemEmptyBinding

class ItemEmptyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    ViewHolderItemEmptyBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ).root
)