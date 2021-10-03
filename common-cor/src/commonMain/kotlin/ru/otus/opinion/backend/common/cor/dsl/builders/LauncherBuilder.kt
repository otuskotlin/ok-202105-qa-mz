package ru.otus.opinion.backend.common.cor.dsl.builders

import ru.otus.opinion.backend.common.cor.*
import ru.otus.opinion.backend.common.cor.dsl.CorDslMarker

/**
 * Use to build a [Launcher].
 */
@CorDslMarker
class LauncherBuilder<T>(private val runner: Runner<T>): BuilderBase<T>() {

    private val innerWorkers: MutableList<Worker<T>> = mutableListOf()

    fun worker(initializer: WorkerBuilder<T>.() -> Unit) {
        val worker = WorkerBuilder<T>().apply(initializer).build()
        innerWorkers.add(worker)
    }

    fun chain(initializer: LauncherBuilder<T>.() -> Unit) {
        val worker = LauncherBuilder(SequentialRunner<T>()).apply(initializer).build()
        innerWorkers.add(worker)
    }

    fun parallel(initializer: LauncherBuilder<T>.() -> Unit) {
        val worker = LauncherBuilder(ParallelRunner<T>()).apply(initializer).build()
        innerWorkers.add(worker)
    }

    internal fun build(): Worker<T> {
        val launcher = Launcher<T>(runner, innerWorkers)
        return ConditionalWorker<T>(launcher, on, onException)
    }
}
