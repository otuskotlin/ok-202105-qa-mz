package ru.otus.opinion.context

import ru.otus.opinion.models.ServerError
import ru.otus.opinion.models.State

interface Context {
    var state: State
    fun addError(error: ServerError): Context
}