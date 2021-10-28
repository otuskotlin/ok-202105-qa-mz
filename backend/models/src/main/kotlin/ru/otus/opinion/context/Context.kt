package ru.otus.opinion.context

import ru.otus.opinion.models.ServerError

interface Context {
    var state: State
    fun addError(error: ServerError): Context
}