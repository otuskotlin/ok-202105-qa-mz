package ru.otus.opinion.logics.workers

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.backend.common.models.ServerErrorModel

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
                ServerErrorModel(message = "Wrong processing chain for request type $requiredRequestType. Expected request type: $requestType")
            )
        }
    }