package ru.otus.opinion.services.workers

import ru.otus.opinion.context.RequestType
import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.models.State
import ru.otus.opinion.services.ServiceContext

internal fun ChainBuilder<ServiceContext>.checkRequestType(
    requiredRequestType: RequestType
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