package com.louis.domain.interactor.outputport

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer

abstract class CustomSingleObserver<T> : CustomObserver() {

    internal fun getSuccessConsumer(): Consumer<T> {
        return Consumer { this.onSuccess(it) }
    }

    open fun onSuccess(@NonNull t: T) {}
}