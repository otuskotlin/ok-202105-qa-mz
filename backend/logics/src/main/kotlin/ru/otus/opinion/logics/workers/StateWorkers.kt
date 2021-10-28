package ru.otus.opinion.logics.workers

import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.context.State
import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.ServerError

internal fun ChainBuilder<RequestContext>.init() =
    worker {
        execute {
            if (state == State.INITIAL) {
                state = State.RUNNING
            } else {
                val contextState = state
                state = State.FAILED
                addError(
                    ServerError(
                        errorType = ErrorType.INITIALIZATION_ERROR,
                        message = "Failed to init processing chain: context state is $contextState but expected state is ${State.INITIAL}"
                    )
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
