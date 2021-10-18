package ru.otus.opinion.backend.common.models

class ServerErrorModel(
    override val field: String = "",
    override val message: String = "",
    override val level: ErrorLevel = ErrorLevel.ERROR
) : ServerError