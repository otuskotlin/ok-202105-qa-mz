package ru.otus.opinion.backend.common.cor.dsl.builders

import ru.otus.opinion.backend.common.cor.ConditionalWorker
import ru.otus.opinion.backend.common.cor.EmptyWorker
import ru.otus.opinion.backend.common.cor.TerminalWorker
import ru.otus.opinion.backend.common.cor.Worker
import ru.otus.opinion.backend.common.cor.dsl.CorDslMarker

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