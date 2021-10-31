package ru.otus.opinion.logics.workers

import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.models.State
import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.ServerError

internal fun ChainBuilder<RequestContext>.checkRequestType(
    requiredRequestType: RequestContext.RequestType
) =
    worker {
        on {
            requestType != requiredRequestType
        }
        execute {
            state = State.FAILED
            addError(
                ServerError(
                    errorType = ErrorType.INITIALIZATION_ERROR,
                    message = "Wrong processing chain for request type $requiredRequestType. Expected request type: $requestType")
            )
        }
    }