package com.example.r.nvice.playground.poc.grid.staggered.ui.widget.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItem
import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItemPayload
import com.example.r.nvice.playground.poc.grid.staggered.model.ViewItemType
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder.Item16x9PlayableViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder.Item16x9ViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder.Item1x1PlayableViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder.Item1x1ViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder.Item3x4PlayableViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder.Item3x4ViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder.ItemEmptyViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder.ItemViewHolder
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.viewholder.PlayableViewHolder

class MainListAdapter : ListAdapter<ViewItem, ViewHolder>(
    ViewItemDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (ViewItemType.from(viewType)) {
            ViewItemType.ITEM_3X4 -> Item3x4ViewHolder(parent)
            ViewItemType.ITEM_3X4_PLAYABLE -> Item3x4PlayableViewHolder(parent)
            ViewItemType.ITEM_1X1 -> Item1x1ViewHolder(parent)
            ViewItemType.ITEM_1X1_PLAYABLE -> Item1x1PlayableViewHolder(parent)
            ViewItemType.ITEM_16X9 -> Item16x9ViewHolder(parent)
            ViewItemType.ITEM_16X9_PLAYABLE -> Item16x9PlayableViewHolder(parent)
            null -> ItemEmptyViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as? ItemViewHolder)?.bind(item, position)
        when (item.type) {
            ViewItemType.ITEM_16X9, ViewItemType.ITEM_16X9_PLAYABLE -> {
                val layoutParams =
                    holder.itemView.layoutParams as? StaggeredGridLayoutManager.LayoutParams
                layoutParams?.isFullSpan = true
            }

            else -> {}
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when (val payload = payloads.lastOrNull()) {
            is ViewItemPayload.Playable -> {
                (holder as? PlayableViewHolder)?.setPlaying(payload.isPlaying)
            }

            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.value
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }
}

class ViewItemDiffCallback : DiffUtil.ItemCallback<ViewItem>() {
    override fun areItemsTheSame(oldItem: ViewItem, newItem: ViewItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ViewItem, newItem: ViewItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ViewItem, newItem: ViewItem): Any? {
        return when {
            oldItem.isPlaying != newItem.isPlaying -> {
                ViewItemPayload.Playable(newItem.isPlaying)
            }

            else -> super.getChangePayload(oldItem, newItem)
        }
    }
}