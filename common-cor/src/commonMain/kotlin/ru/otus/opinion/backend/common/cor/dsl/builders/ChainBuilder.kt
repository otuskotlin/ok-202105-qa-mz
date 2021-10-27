package ru.otus.opinion.backend.common.cor.dsl.builders

import ru.otus.opinion.backend.common.cor.*
import ru.otus.opinion.backend.common.cor.dsl.CorDslMarker

/**
 * Use to build a [Launcher].
 */
@CorDslMarker
class ChainBuilder<T>(private val runner: Runner<T>): BuilderBase<T>() {

    private val innerWorkers: MutableList<Worker<T>> = mutableListOf()

    fun worker(initializer: WorkerBuilder<T>.() -> Unit) {
        val worker = WorkerBuilder<T>().apply(initializer).build()
        innerWorkers.add(worker)
    }

    fun chain(initializer: ChainBuilder<T>.() -> Unit) {
        val worker = ChainBuilder(SequentialRunner<T>()).apply(initializer).build()
        innerWorkers.add(worker)
    }

    fun parallel(initializer: ChainBuilder<T>.() -> Unit) {
        val worker = ChainBuilder(ParallelRunner<T>()).apply(initializer).build()
        innerWorkers.add(worker)
    }

    internal fun build(): Worker<T> {
        val launcher = Launcher(runner, innerWorkers)
        return ConditionalWorker(launcher, on, onException)
    }
}
