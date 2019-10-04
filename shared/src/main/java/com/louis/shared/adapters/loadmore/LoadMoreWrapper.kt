package com.louis.shared.adapters.loadmore

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

@Suppress("unused")
class LoadMoreWrapper private constructor(private val loadMoreAdapter: LoadMoreAdapter) {

    val progressView: View?
        get() = loadMoreAdapter.progressView

    val reachEndView: View?
        get() = loadMoreAdapter.reachEndView

    val loadFailedView: View?
        get() = loadMoreAdapter.loadFailedView

    val originalAdapter: RecyclerView.Adapter<*>
        get() = loadMoreAdapter.originalAdapter

    fun setProgressView(@LayoutRes resId: Int): LoadMoreWrapper {
        loadMoreAdapter.setProgressView(resId)
        return this
    }

    fun setProgressView(progressView: View): LoadMoreWrapper {
        loadMoreAdapter.progressView = progressView
        return this
    }

    fun setReachEndView(@LayoutRes resId: Int): LoadMoreWrapper {
        loadMoreAdapter.setReachEndView(resId)
        return this
    }

    fun setReachEndView(reachEndView: View): LoadMoreWrapper {
        loadMoreAdapter.reachEndView = reachEndView
        return this
    }

    fun setLoadFailedView(@LayoutRes resId: Int): LoadMoreWrapper {
        loadMoreAdapter.setLoadFailedView(resId)
        return this
    }

    fun setLoadFailedView(view: View): LoadMoreWrapper {
        loadMoreAdapter.loadFailedView = view
        return this
    }

    fun setLoadMoreListener(listener: LoadMoreAdapter.OnLoadMoreListener): LoadMoreWrapper {
        loadMoreAdapter.setLoadMoreListener(listener)
        return this
    }

    fun setShowReachEnd(enabled: Boolean): LoadMoreWrapper {
        loadMoreAdapter.setShowReachEnd(enabled)
        return this
    }

    fun setFailedMessage(message: String): LoadMoreWrapper {
        loadMoreAdapter.retryMessage = message
        return this
    }

    fun setReachEndMessage(message: String): LoadMoreWrapper {
        loadMoreAdapter.reachEndMessage = message
        return this
    }

    /**
     * Set your threshold items number. With this, your adapter's count must be equals or greater than
     * this number to activating the load more callback. The default number is 5
     */
    fun setVisibleThreshold(visibleThreshold: Int): LoadMoreWrapper {
        loadMoreAdapter.visibleThreshold = visibleThreshold
        return this
    }

    fun into(recyclerView: RecyclerView): LoadMoreAdapter {
        recyclerView.adapter = loadMoreAdapter
        return loadMoreAdapter
    }

    companion object {
        fun with(adapter: RecyclerView.Adapter<*>): LoadMoreWrapper {
            return LoadMoreWrapper(LoadMoreAdapter(adapter))
        }
    }
}
