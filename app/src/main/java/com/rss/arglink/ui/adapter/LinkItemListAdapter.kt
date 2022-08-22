package com.rss.arglink.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.rss.arglink.data.model.LinkItem

class LinkItemListAdapter : ListAdapter<LinkItem, LinkItemListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkItemListViewHolder {
        return LinkItemListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LinkItemListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LinkItem>() {
            override fun areItemsTheSame(oldItem: LinkItem, newItem: LinkItem): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(oldItem: LinkItem, newItem: LinkItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}