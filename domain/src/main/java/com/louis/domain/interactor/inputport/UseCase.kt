package com.louis.domain.interactor.inputport

import com.louis.domain.executor.PostExecutionThread
import com.louis.domain.executor.ThreadExecutor
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class UseCase<in I : UseCase.Input, O>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    private val compositeDisposable = CompositeDisposable()

    fun subscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun dispose() {
        compositeDisposable.clear()
    }

    protected open fun getSubscribeOnScheduler(): Scheduler {
        return Schedulers.from(threadExecutor)
    }

    protected open fun getObserveOnScheduler(): Scheduler {
        return postExecutionThread.scheduler
    }

    abstract fun buildUseCaseObservable(input: I): O
    abstract class Input

    open class EmptyInput : Input() {

        companion object {

            fun instance(): EmptyInput {
                return EmptyInput()
            }
        }
    }
}