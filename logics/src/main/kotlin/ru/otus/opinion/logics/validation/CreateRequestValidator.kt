package ru.otus.opinion.logics.validation

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.validation.validators.StringIsNotEmptyValidator

internal fun ChainBuilder<RequestContext>.validateCreateRequest() =
    chain{
        on {
            state == State.RUNNING
        }
        chain {
            validate { requestQuestion.title } with StringIsNotEmptyValidator("title")
        }
    }
