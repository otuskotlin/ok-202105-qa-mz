package ru.otus.opinion.services.validation

import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.models.State
import ru.otus.opinion.services.ServiceContext
import ru.otus.opinion.validation.validators.RangeValidator

internal fun ChainBuilder<ServiceContext>.validateQuestionsListRequest() =
    chain {
        on {
            state == State.RUNNING
        }
        chain {
            validate { this.pagination.count } with RangeValidator("questions count", 0, 1000)
        }
    }
