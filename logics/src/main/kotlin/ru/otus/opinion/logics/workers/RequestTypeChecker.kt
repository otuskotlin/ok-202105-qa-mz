package ru.otus.opinion.logics.workers

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.backend.common.models.ErrorType
import ru.otus.opinion.backend.common.models.ServerError

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