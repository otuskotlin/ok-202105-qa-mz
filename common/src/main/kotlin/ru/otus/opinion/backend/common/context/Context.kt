package ru.otus.opinion.backend.common.context

import ru.otus.opinion.backend.common.models.ServerError

interface Context {
    var state: State
    fun addError(error: ServerError): Context
}