package ru.otus.opinion.services.workers

import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.models.State
import ru.otus.opinion.services.ServiceContext

internal fun ChainBuilder<ServiceContext>.init() =
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

internal fun ChainBuilder<ServiceContext>.finish() =
    worker {
        on {
            state != State.FAILED
        }
        execute {
            state = State.SUCCESS
        }
    }
