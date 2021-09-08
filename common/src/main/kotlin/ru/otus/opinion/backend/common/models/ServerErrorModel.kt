package ru.otus.opinion.backend.common.models

class ServerErrorModel(
    override val field: String = "",
    override val level: ErrorLevel = ErrorLevel.ERROR,
    override val message: String = ""
) : ServerError