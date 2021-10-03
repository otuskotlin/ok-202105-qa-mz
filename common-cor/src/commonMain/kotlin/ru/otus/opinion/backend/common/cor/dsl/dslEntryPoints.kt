package ru.otus.opinion.backend.common.cor.dsl

import ru.otus.opinion.backend.common.cor.ParallelRunner
import ru.otus.opinion.backend.common.cor.SequentialRunner
import ru.otus.opinion.backend.common.cor.Worker
import ru.otus.opinion.backend.common.cor.dsl.builders.LauncherBuilder
import ru.otus.opinion.backend.common.cor.dsl.builders.WorkerBuilder

/**
 * DSL functions available on top level and used to start building a [Worker].
 */

fun <T> worker(initializer: WorkerBuilder<T>.() -> Unit): Worker<T> =
    WorkerBuilder<T>().apply(initializer).build()


fun <T> parallel(initializer: LauncherBuilder<T>.() -> Unit): Worker<T> =
    LauncherBuilder(ParallelRunner<T>()) .apply(initializer).build()

fun <T> chain(initializer: LauncherBuilder<T>.() -> Unit): Worker<T> =
    LauncherBuilder(SequentialRunner<T>()).apply(initializer).build()

