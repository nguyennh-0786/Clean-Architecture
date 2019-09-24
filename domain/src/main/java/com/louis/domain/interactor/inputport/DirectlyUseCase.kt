package com.louis.domain.interactor.inputport

abstract class DirectlyUseCase<in I : UseCase.Input?, O> {

    abstract fun buildUseCase(input: I): O

    fun execute(input: I): O {
        return buildUseCase(input)
    }
}
