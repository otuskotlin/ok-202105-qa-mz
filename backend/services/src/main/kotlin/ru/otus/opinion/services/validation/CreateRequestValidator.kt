package ru.otus.opinion.services.validation

import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.models.State
import ru.otus.opinion.services.ServiceContext
import ru.otus.opinion.validation.validators.StringIsNotEmptyValidator

internal fun ChainBuilder<ServiceContext>.validateCreateRequest() =
    chain{
        on {
            state == State.RUNNING
        }
        chain {
            validate { requestQuestion.title } with StringIsNotEmptyValidator("title")
        }
    }
