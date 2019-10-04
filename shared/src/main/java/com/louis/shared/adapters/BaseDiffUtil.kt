package com.louis.shared.adapters

import androidx.recyclerview.widget.DiffUtil

open class BaseDiffUtil<T : RecyclerViewItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        // Override this if your item have an id
        return true
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        // 2 ways to make this happened
        // - Simple way: make your [T] item is data class
        // - Performance way: make your own [T] item's equals() & hashcode() methods
        return oldItem == newItem
    }
}
