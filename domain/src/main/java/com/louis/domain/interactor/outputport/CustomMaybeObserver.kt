package com.louis.domain.interactor.outputport

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

abstract class CustomMaybeObserver<T> : CustomObserver() {

    internal fun getSuccessConsumer(): Consumer<T> {
        return Consumer { this.onSuccess(it) }
    }

    internal fun onCompleteAction(): Action {
        return Action { onCompleted() }
    }

    open fun onCompleted() {}

    open fun onSuccess(@NonNull t: T) {}
}