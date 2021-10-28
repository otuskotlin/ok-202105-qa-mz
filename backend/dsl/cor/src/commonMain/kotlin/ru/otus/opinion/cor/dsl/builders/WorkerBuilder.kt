package ru.otus.opinion.cor.dsl.builders

import ru.otus.opinion.cor.ConditionalWorker
import ru.otus.opinion.cor.EmptyWorker
import ru.otus.opinion.cor.TerminalWorker
import ru.otus.opinion.cor.Worker
import ru.otus.opinion.cor.dsl.CorDslMarker

/**
 * Use to build [Worker]
 */
@CorDslMarker
class WorkerBuilder<T>: BuilderBase<T>() {
    private var innerWorker: Worker<T> = EmptyWorker<T>()

    /**
     * Actual code executed by this worker.
     */
    fun execute(function: suspend T.() -> Unit) {
        innerWorker = TerminalWorker<T>(function)
    }

    internal fun build(): Worker<T> = ConditionalWorker(innerWorker, on, onException)
}