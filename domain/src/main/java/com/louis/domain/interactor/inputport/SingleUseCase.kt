package com.louis.domain.interactor.inputport

import com.louis.domain.executor.PostExecutionThread
import com.louis.domain.executor.ThreadExecutor
import com.louis.domain.interactor.outputport.CustomSingleObserver
import io.reactivex.Single

abstract class SingleUseCase<in I : UseCase.Input, O>(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<I, Single<O>>(
    threadExecutor,
    postExecutionThread
) {

    fun execute(input: I, observer: CustomSingleObserver<O>) {
        val observable = this.buildUseCaseObservable(input)
            .subscribeOn(getSubscribeOnScheduler())
            .observeOn(getObserveOnScheduler())
            .doOnSubscribe(observer.onSubscribeConsumer())
            .doAfterTerminate(observer.doAfterTerminateAction())
            .doFinally(observer.doFinallyAction())
        subscribe(
            observable.subscribe(
                observer.getSuccessConsumer(),
                observer.onErrorConsumer()
            )
        )
    }
}
