package ru.otus.opinion.models

class ServerError(
    val field: String = "",
    val message: String = "",
    val level: ErrorLevel = ErrorLevel.ERROR,
    val errorType: ErrorType = ErrorType.SERVER_ERROR
) {
    constructor(ex: Throwable) : this(message = "Server error: ${ex.message}")
}