package com.louis.shared.adapters.loadmore

class LoadMoreController(private val onLoadMoreStateChangeListener: OnLoadMoreStateChangeListener) {
    /**
     * Get whether loading is enable more, the default is true
     * Set whether to enable loading more
     */
    var loadMoreEnabled = true
        set(enabled) {
            val canNotify = loadMoreEnabled
            field = enabled

            if (canNotify && !loadMoreEnabled) {
                onLoadMoreStateChangeListener.notifyChanged()
            }
        }
    private var isLoadFailed = false
    private var hasReachedEnd = false

    /**
     * Set whether the load failed
     *
     * @param isFailed Whether the load failed
     */
    fun setLoadFailed(isFailed: Boolean) {
        if (isLoadFailed != isFailed) {
            isLoadFailed = isFailed
            loadMoreEnabled = !isLoadFailed
            onLoadMoreStateChangeListener.notifyLoadFailed(isFailed)
        }
    }

    /**
     * Set whether no more load
     *
     * @param reachEnd Whether no more load
     */
    fun setReachEnd(reachEnd: Boolean) {
        if (hasReachedEnd != reachEnd) {
            hasReachedEnd = reachEnd
            loadMoreEnabled = !hasReachedEnd
            onLoadMoreStateChangeListener.notifyReachEnd(reachEnd)
        }
    }

    interface OnLoadMoreStateChangeListener {
        fun notifyChanged()

        fun notifyLoadFailed(isFailed: Boolean)

        fun notifyReachEnd(reachEnd: Boolean)
    }
}
