package ru.otus.opinion.cor.dsl

import ru.otus.opinion.cor.ParallelRunner
import ru.otus.opinion.cor.SequentialRunner
import ru.otus.opinion.cor.Worker
import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.cor.dsl.builders.WorkerBuilder

/**
 * DSL functions available on top level and used to start building a [Worker].
 */

fun <T> worker(initializer: WorkerBuilder<T>.() -> Unit): Worker<T> =
    WorkerBuilder<T>().apply(initializer).build()


fun <T> parallel(initializer: ChainBuilder<T>.() -> Unit): Worker<T> =
    ChainBuilder(ParallelRunner<T>()) .apply(initializer).build()

fun <T> chain(initializer: ChainBuilder<T>.() -> Unit): Worker<T> =
    ChainBuilder(SequentialRunner<T>()).apply(initializer).build()

