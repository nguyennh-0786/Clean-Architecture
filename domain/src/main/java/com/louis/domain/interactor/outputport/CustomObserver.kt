package com.louis.domain.interactor.outputport

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

abstract class CustomObserver {

    internal fun onErrorConsumer(): Consumer<in Throwable> {
        return Consumer { onError(it) }
    }

    internal fun onSubscribeConsumer(): Consumer<Any> {
        return Consumer { onSubscribe() }
    }

    internal fun doAfterTerminateAction(): Action {
        return Action { doAfterTerminate() }
    }

    internal fun doFinallyAction(): Action {
        return Action { doFinally() }
    }

    open fun onError(throwable: Throwable) {}

    open fun onSubscribe() {}

    open fun doAfterTerminate() {}

    open fun doFinally() {}

}