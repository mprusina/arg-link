package com.rss.arglink.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rss.arglink.data.model.LinkItem
import com.rss.arglink.databinding.FragmentLinkItemListRowBinding

class LinkItemListViewHolder(private val binding: FragmentLinkItemListRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LinkItem) {
        Glide.with(binding.root.context)
            .load(item.logoUrl)
            .centerCrop()
            .into(binding.logo)
        binding.name.text = item.name
        binding.category.text = item.kind
    }

    companion object {
        fun create(parent: ViewGroup): LinkItemListViewHolder {
            val listItemBinding = FragmentLinkItemListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LinkItemListViewHolder(listItemBinding)
        }
    }
}