package ru.otus.opinion.backend.common.cor.dsl.builders

open class BuilderBase<T> {
    protected var on: suspend T.() -> Boolean = { true }
    protected var onException: suspend T.(e: Throwable) -> Unit = { e: Throwable -> throw e }

    /**
     * Predicate used to decide if the worker will call its execute() method.
     */
    fun on(function: suspend T.() -> Boolean) {
        on = function
    }

    /**
     * Code called if an exception was thrown during execute() method runs.
     */
    fun onException(function: suspend T.(e: Throwable) -> Unit) {
        onException = function
    }
}