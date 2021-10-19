package ru.otus.opinion.backend.common.models

class ServerError(
    val field: String = "",
    val message: String = "",
    val level: ErrorLevel = ErrorLevel.ERROR
)