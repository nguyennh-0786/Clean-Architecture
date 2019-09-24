package com.louis.domain.interactor.outputport

import io.reactivex.functions.Action

abstract class CustomCompletableObserver : CustomObserver() {

    internal fun onCompleteAction(): Action {
        return Action { onCompleted() }
    }

    open fun onCompleted() {}

}
