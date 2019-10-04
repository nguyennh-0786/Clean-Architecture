package com.louis.shared.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import com.louis.shared.adapters.BaseDiffUtil
import com.louis.shared.adapters.BaseViewHolder
import com.louis.shared.adapters.RecyclerType
import com.louis.shared.adapters.RecyclerViewItem

@Suppress("UNCHECKED_CAST")
abstract class BaseAdapter protected constructor(
    diffCallback: BaseDiffUtil<*> = BaseDiffUtil<RecyclerViewItem>()
) : ListAdapter<RecyclerViewItem, BaseViewHolder<in RecyclerViewItem>>(
    diffCallback as BaseDiffUtil<in RecyclerViewItem>
) {

    abstract fun customViewHolder(parent: ViewGroup, viewType: Int): Any

    final override fun onBindViewHolder(
        baseViewHolder: BaseViewHolder<in RecyclerViewItem>,
        position: Int
    ) {
        baseViewHolder.bind(getItem(position))
    }

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<in RecyclerViewItem> {
        val holder = customViewHolder(parent, viewType) as? BaseViewHolder<*>
            ?: throw ClassCastException(
                "Please create BaseViewHolder for a child class of RecyclerViewItem !"
            )
        return holder as BaseViewHolder<in RecyclerViewItem>
    }

    @RecyclerType
    final override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    final override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    protected fun inflateView(@LayoutRes layoutRes: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    }
}
