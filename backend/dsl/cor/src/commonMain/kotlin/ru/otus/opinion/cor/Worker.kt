package ru.otus.opinion.cor

/**
 * DSL builds implementation of this interface.
 */
interface Worker<T> {
    suspend fun process(context: T)
}

/**
 * This worker does nothing.
 */
class EmptyWorker<T>: Worker<T> {
    override suspend fun process(context: T) {}
}

/**
 * Just run provided function on context.
 */
class TerminalWorker<T>(val function: suspend T.() -> Unit): Worker<T> {
    override suspend fun process(context: T) = function(context)
}

/**
 * Launches inner worker on condition and handle exceptions.
 */
class ConditionalWorker<T>(
    private val innerWorker: Worker<T>,
    private val condition: suspend T.() -> Boolean,
    private val handleException: suspend T.(Throwable) -> Unit
): Worker<T> {
    override suspend fun process(context: T) {
        if (condition(context)) {
            try {
                innerWorker.process(context)
            } catch (ex: Throwable) {
                handleException(context, ex)
            }
        }
    }
}

/**
 * Launches inner workers with specified runner.
 */
class Launcher<T>(
    private val runner: Runner<T>,
    private val innerWorkers: List<Worker<T>>
) : Worker<T> {
    override suspend fun process(context: T) = runner.run(innerWorkers, context)
}
