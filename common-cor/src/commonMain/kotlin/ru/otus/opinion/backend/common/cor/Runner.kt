package ru.otus.opinion.backend.common.cor

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Helper used to implement [Worker].
 */
interface Runner<T> {
    suspend fun run(workers : List<Worker<T>>, context: T)
}

class SequentialRunner<T> : Runner<T> {
    override suspend fun run(workers: List<Worker<T>>, context: T) =
        workers.forEach { it.process(context) }
}

class ParallelRunner<T> : Runner<T> {
    override suspend fun run(workers: List<Worker<T>>, context: T) =
        coroutineScope {
            workers.map { launch { it.process(context) } }.forEach { it.join() }
        }
}