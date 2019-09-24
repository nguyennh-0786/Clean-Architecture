package com.louis.domain.interactor.inputport

import com.louis.domain.executor.PostExecutionThread
import com.louis.domain.executor.ThreadExecutor
import com.louis.domain.interactor.outputport.CustomMaybeObserver
import io.reactivex.Maybe

abstract class MaybeUseCase<in I : UseCase.Input, O>(
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) : UseCase<I, Maybe<O>>(threadExecutor, postExecutionThread) {

    fun execute(input: I, observer: CustomMaybeObserver<O>) {
        val observable = this.buildUseCaseObservable(input)
            .subscribeOn(getSubscribeOnScheduler())
            .observeOn(getObserveOnScheduler())
            .doOnSubscribe(observer.onSubscribeConsumer())
            .doAfterTerminate(observer.doAfterTerminateAction())
            .doFinally(observer.doFinallyAction())
        subscribe(
            observable.subscribe(
                observer.getSuccessConsumer(),
                observer.onErrorConsumer(), observer.onCompleteAction()
            )
        )
    }
}