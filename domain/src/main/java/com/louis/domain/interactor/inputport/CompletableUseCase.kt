package com.louis.domain.interactor.inputport

import com.louis.domain.executor.PostExecutionThread
import com.louis.domain.executor.ThreadExecutor
import com.louis.domain.interactor.outputport.CustomCompletableObserver
import io.reactivex.Completable

abstract class CompletableUseCase<in I : UseCase.Input>(
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) : UseCase<I, Completable>(threadExecutor, postExecutionThread) {

    fun execute(input: I, observer: CustomCompletableObserver) {
        val observable = this.buildUseCaseObservable(input)
            .subscribeOn(getSubscribeOnScheduler())
            .observeOn(getObserveOnScheduler())
            .doOnSubscribe(observer.onSubscribeConsumer())
            .doAfterTerminate(observer.doAfterTerminateAction())
            .doFinally(observer.doFinallyAction())
        subscribe(observable.subscribe(observer.onCompleteAction(),
            observer.onErrorConsumer()))
    }
}
