package ru.otus.opinion.logics.validation

import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.context.State
import ru.otus.opinion.cor.dsl.builders.ChainBuilder
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
