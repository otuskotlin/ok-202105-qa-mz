package ru.otus.opinion.logics.workers

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.backend.common.models.ServerErrorModel

internal fun ChainBuilder<RequestContext>.init() =
    worker {
        execute {
            if (state == State.INITIAL) {
                state = State.RUNNING
            } else {
                val contextState = state
                state = State.FAILED
                addError(
                    ServerErrorModel(message = """
                        Failed to init processing chain: context state is $contextState but expected state is ${State.INITIAL}  
                    """.trimIndent())
                )
            }
        }
    }

internal fun ChainBuilder<RequestContext>.finish() =
    worker {
        on {
            state != State.FAILED
        }
        execute {
            state = State.SUCCESS
        }
    }
