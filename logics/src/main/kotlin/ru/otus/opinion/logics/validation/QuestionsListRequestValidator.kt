package ru.otus.opinion.logics.validation

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.validation.validators.RangeValidator

internal fun ChainBuilder<RequestContext>.validateQuestionsListRequest() =
    chain {
        on {
            state == State.RUNNING
        }
        chain {
            validate { this.pagination.count } with RangeValidator("questions count", 0, 1000)
        }
    }
