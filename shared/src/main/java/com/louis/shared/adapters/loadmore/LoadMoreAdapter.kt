package com.louis.shared.adapters.loadmore

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.louis.shared.R

class LoadMoreAdapter internal constructor(
    adapter: RecyclerView.Adapter<*>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var progressView: View? = null
    private var progressResId = View.NO_ID

    var reachEndView: View? = null
    private var reachEndResId = View.NO_ID

    var loadFailedView: View? = null
    private var loadFailedResId = View.NO_ID

    var retryMessage: String? = null
    var reachEndMessage: String? = null

    /**
     * At least your adapter count must be greater or equal this number for activating the load more callback
     */
    var visibleThreshold: Int = DEFAULT_THRESHOLD

    lateinit var originalAdapter: RecyclerView.Adapter<*>
    private var recyclerView: RecyclerView? = null
    private var onLoadMoreListener: OnLoadMoreListener? = null

    private lateinit var loadMoreController: LoadMoreController
    private var isLoading: Boolean = false
    private var shouldRemove: Boolean = false
    private var showReachEnd: Boolean = false
    private var isLoadFailed: Boolean = false
    private var hasReachedEnd: Boolean = false


    /**
     * Deciding whether to trigger loading
     */
    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (!loadMoreEnabled || isLoading || onLoadMoreListener == null) {
                return
            }

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                val layoutManager = recyclerView.layoutManager
                val isBottom = when (layoutManager) {
                    is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition() >= layoutManager.getItemCount() - 1
                    is StaggeredGridLayoutManager -> {
                        val into = IntArray(layoutManager.spanCount)
                        layoutManager.findLastVisibleItemPositions(into)

                        last(into) >= layoutManager.getItemCount() - 1
                    }
                    else -> (layoutManager as GridLayoutManager).findLastVisibleItemPosition() >= layoutManager.getItemCount() - 1
                }

                if (isBottom && itemCount >= visibleThreshold) {
                    isLoading = true
                    onLoadMoreListener!!.onLoadMore()
                }
            }
        }
    }

    var loadMoreEnabled: Boolean
        get() = loadMoreController.loadMoreEnabled && originalAdapter.itemCount >= 0
        set(enabled) {
            this.loadMoreController.loadMoreEnabled = enabled
        }

    private val onLoadMoreStateChangedListener = object :
        LoadMoreController.OnLoadMoreStateChangeListener {
        override fun notifyChanged() {
            shouldRemove = true
        }

        override fun notifyLoadFailed(isFailed: Boolean) {
            isLoadFailed = isFailed
            notifyFooterHolderChanged()
        }

        override fun notifyReachEnd(reachEnd: Boolean) {
            hasReachedEnd = reachEnd
            notifyFooterHolderChanged()
        }
    }

    private val dataObserver: RecyclerView.AdapterDataObserver =
        object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (shouldRemove) {
                    shouldRemove = false
                }
                this@LoadMoreAdapter.notifyDataSetChanged()
                isLoading = false
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                if (shouldRemove && positionStart == originalAdapter.itemCount) {
                    shouldRemove = false
                }

                if (positionStart == 0) {
                    this@LoadMoreAdapter.notifyDataSetChanged()
                } else {
                    this@LoadMoreAdapter.notifyItemRangeChanged(positionStart, itemCount)
                }

                isLoading = false
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                if (shouldRemove && positionStart == originalAdapter.itemCount) {
                    shouldRemove = false
                }

                if (positionStart == 0) {
                    this@LoadMoreAdapter.notifyDataSetChanged()
                } else {
                    this@LoadMoreAdapter.notifyItemRangeChanged(positionStart, itemCount, payload)
                }

                isLoading = false
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                // when no data is initialized (has loadMoreView)
                // should remove loadMoreView before notifyItemRangeInserted
                if (getItemCount() == 1) {
                    this@LoadMoreAdapter.notifyItemRemoved(0)
                }

                if (positionStart == 0) {
                    this@LoadMoreAdapter.notifyDataSetChanged()
                } else {
                    this@LoadMoreAdapter.notifyItemRangeInserted(positionStart, itemCount)
                }

                notifyFooterHolderChanged()
                isLoading = false
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                if (shouldRemove && positionStart == originalAdapter.itemCount) {
                    shouldRemove = false
                }
                /*
                   use notifyItemRangeRemoved after clear item, can throw IndexOutOfBoundsException
                   @link RecyclerView#tryGetViewHolderForPositionByDeadline
                   fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position
                 */
                var shouldSync = false
                if (loadMoreController.loadMoreEnabled && originalAdapter.itemCount == 0) {
                    loadMoreEnabled = false
                    shouldSync = true
                    // when use onItemRangeInserted(0, count) after clear item
                    // recyclerView will auto scroll to bottom, because has one item(loadMoreView)
                    // remove loadMoreView
                    if (getItemCount() == 1) {
                        this@LoadMoreAdapter.notifyItemRemoved(0)
                    }
                }

                if (positionStart == 0) {
                    this@LoadMoreAdapter.notifyDataSetChanged()
                } else {
                    this@LoadMoreAdapter.notifyItemRangeRemoved(positionStart, itemCount)
                }

                if (shouldSync) {
                    loadMoreEnabled = true
                }
                isLoading = false
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                if (shouldRemove && (fromPosition == originalAdapter.itemCount || toPosition == originalAdapter.itemCount)) {
                    throw IllegalArgumentException(
                        "can not move last position after setLoadMoreEnabled(false)"
                    )
                }

                this@LoadMoreAdapter.notifyItemMoved(fromPosition, toPosition)
                isLoading = false
            }
        }

    init {
        registerAdapter(adapter)
    }

    private fun registerAdapter(adapter: RecyclerView.Adapter<*>?) {
        if (adapter == null) {
            throw NullPointerException("adapter can not be null!")
        }

        originalAdapter = adapter
        originalAdapter.registerAdapterDataObserver(dataObserver)
        loadMoreController = LoadMoreController(onLoadMoreStateChangedListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_PROGRESS -> {
                if (progressResId != View.NO_ID) {
                    progressView = LoadMoreHelper.inflate(parent, progressResId)
                }
                if (progressView != null) {
                    return ProgressHolder(progressView!!)
                }
                return ProgressHolder(LoadMoreHelper.inflate(parent, R.layout.layout_progress))
            }
            TYPE_REACH_END -> {
                if (reachEndResId != View.NO_ID) {
                    reachEndView = LoadMoreHelper.inflate(parent, reachEndResId)
                }
                if (reachEndView != null) {
                    return ReachEndHolder(reachEndView!!)
                }
                return ReachEndHolder(LoadMoreHelper.inflate(parent, R.layout.layout_info))
            }
            TYPE_LOAD_FAILED -> {
                if (loadFailedResId != View.NO_ID) {
                    loadFailedView = LoadMoreHelper.inflate(parent, loadFailedResId)
                }
                var view = loadFailedView
                if (view == null) {
                    view = LoadMoreHelper.inflate(parent, R.layout.layout_retry)
                }
                return LoadFailedHolder(view, loadMoreController, onLoadMoreListener)
            }
            else -> return originalAdapter.onCreateViewHolder(parent, viewType)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int,
        payloads: List<Any>
    ) {
        when (holder) {
            is ProgressHolder -> {
                // TODO Checking later
                // Fix LoadMore in Playlist Detail when dragging to reorder videos of playlist
                // Removed !canScroll(recyclerView?.layoutManager?.layoutDirection ?: RecyclerView.VERTICAL)
                if (onLoadMoreListener != null && !isLoading && itemCount >= visibleThreshold) {

                    isLoading = true
                    // fix Cannot call this method while RecyclerView is computing a layout or scrolling
                    recyclerView?.post { onLoadMoreListener!!.onLoadMore() }
                }
                holder.bind(originalAdapter.itemCount >= visibleThreshold)
            }
            is ReachEndHolder -> {
                holder.bind(reachEndMessage)
            }
            is LoadFailedHolder -> {
                holder.bind(retryMessage)
            }
            else -> {
                @Suppress("UNCHECKED_CAST")
                (originalAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>).onBindViewHolder(
                    holder, position, payloads
                )
            }
        }
    }

    override fun getItemCount(): Int {
        val count = originalAdapter.itemCount
        if (count == 0) return 0
        return when {
            loadMoreEnabled -> count + 1
            showReachEnd -> count + 1
            else -> count + if (shouldRemove) 1 else 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        val isLastItem = position == originalAdapter.itemCount
        if (isLastItem && isLoadFailed) {
            return TYPE_LOAD_FAILED
        }
        if (isLastItem && (loadMoreEnabled || shouldRemove)) {
            return TYPE_PROGRESS
        } else if (isLastItem && showReachEnd && !loadMoreEnabled) {
            return TYPE_REACH_END
        }
        return originalAdapter.getItemViewType(position)
    }

    private fun canScroll(layoutDirection: Int): Boolean {
        if (recyclerView == null) {
            throw NullPointerException(
                "recyclerView is null, you should setAdapter(recyclerAdapter);"
            )
        }
        return recyclerView?.canScrollVertically(layoutDirection) ?: false
    }

    fun setProgressView(@LayoutRes resId: Int) {
        progressResId = resId
    }

    fun setReachEndView(@LayoutRes resId: Int) {
        reachEndResId = resId
    }

    fun setLoadFailedView(@LayoutRes resId: Int) {
        loadFailedResId = resId
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        recyclerView.addOnScrollListener(onScrollListener)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val originalSizeLookup = layoutManager.spanSizeLookup
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val itemViewType = getItemViewType(position)
                    if (itemViewType == TYPE_PROGRESS
                        || itemViewType == TYPE_REACH_END
                        || itemViewType == TYPE_LOAD_FAILED
                    ) {
                        return layoutManager.spanCount
                    } else if (originalSizeLookup != null) {
                        return originalSizeLookup.getSpanSize(position)
                    }

                    return 1
                }
            }
        }
    }

    private fun last(lastPositions: IntArray): Int {
        var last = lastPositions[0]
        for (value in lastPositions) {
            if (value > last) {
                last = value
            }
        }
        return last
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        recyclerView.removeOnScrollListener(onScrollListener)
        originalAdapter.unregisterAdapterDataObserver(dataObserver)
        this.recyclerView = null
    }

    fun setLoadMoreListener(listener: OnLoadMoreListener) {
        onLoadMoreListener = listener
        onLoadMoreListener?.onPrepared(loadMoreController)
    }

    fun setShowReachEnd(enabled: Boolean) {
        showReachEnd = enabled
    }

    private fun notifyFooterHolderChanged() {
        if (loadMoreEnabled) {
            this@LoadMoreAdapter.notifyItemChanged(originalAdapter.itemCount)
        } else if (shouldRemove) {
            shouldRemove = false
            /*
              fix IndexOutOfBoundsException when setLoadMoreEnabled(false) and then use onItemRangeInserted
              @see android.support.v7.widget.RecyclerView.Recycler#validateViewHolderForOffsetPosition(RecyclerView.ViewHolder)
             */
            val position = originalAdapter.itemCount
            val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position)
            if (viewHolder is ProgressHolder) {
                this@LoadMoreAdapter.notifyItemRemoved(position)
            } else {
                this@LoadMoreAdapter.notifyItemChanged(position)
            }
        }
    }

    class ProgressHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            LoadMoreHelper.setItemViewFullSpan(itemView)
        }

        fun bind(isVisible: Boolean) {
            if (isVisible == itemView.isVisible) return
            itemView.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        }
    }

    class ReachEndHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textInfo = itemView.findViewById<TextView>(R.id.info_message)

        init {
            LoadMoreHelper.setItemViewFullSpan(itemView)
        }

        fun bind(reachEndMsg: String?) {
            if (textInfo != null && !reachEndMsg.isNullOrEmpty()) {
                textInfo.text = reachEndMsg
            }
        }
    }

    class LoadFailedHolder internal constructor(
        itemView: View,
        private val loadMoreController: LoadMoreController,
        val listener: OnLoadMoreListener?
    ) : RecyclerView.ViewHolder(itemView) {
        private val btnRetry = itemView.findViewById<View>(R.id.retry_button)
        private val textRetry = itemView.findViewById<TextView>(R.id.retry_message)

        init {
            LoadMoreHelper.setItemViewFullSpan(itemView)
            if (btnRetry != null) {
                btnRetry.setOnClickListener {
                    reload()
                }
            } else {
                itemView.setOnClickListener {
                    reload()
                }
            }
        }

        fun bind(retryMsg: String?) {
            if (textRetry != null && !retryMsg.isNullOrEmpty()) {
                textRetry.text = retryMsg
            }
        }

        private fun reload() {
            loadMoreController.setLoadFailed(false)
            listener?.onLoadMore()
        }
    }

    interface OnLoadMoreListener {
        fun onPrepared(loadMoreController: LoadMoreController?)

        fun onLoadMore()
    }

    companion object {
        // DON'T make your Recycler's ViewType like below
        const val TYPE_PROGRESS = 99997
        const val TYPE_REACH_END = 99998
        const val TYPE_LOAD_FAILED = 99999

        const val DEFAULT_THRESHOLD = 10
    }
}
