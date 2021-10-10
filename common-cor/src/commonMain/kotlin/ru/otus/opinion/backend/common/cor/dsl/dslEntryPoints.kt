package ru.otus.opinion.backend.common.cor.dsl

import ru.otus.opinion.backend.common.cor.ParallelRunner
import ru.otus.opinion.backend.common.cor.SequentialRunner
import ru.otus.opinion.backend.common.cor.Worker
import ru.otus.opinion.backend.common.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.backend.common.cor.dsl.builders.WorkerBuilder

/**
 * DSL functions available on top level and used to start building a [Worker].
 */

fun <T> worker(initializer: WorkerBuilder<T>.() -> Unit): Worker<T> =
    WorkerBuilder<T>().apply(initializer).build()


fun <T> parallel(initializer: ChainBuilder<T>.() -> Unit): Worker<T> =
    ChainBuilder(ParallelRunner<T>()) .apply(initializer).build()

fun <T> chain(initializer: ChainBuilder<T>.() -> Unit): Worker<T> =
    ChainBuilder(SequentialRunner<T>()).apply(initializer).build()

