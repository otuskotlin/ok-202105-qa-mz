package ru.otus.opinion.models

enum class ErrorType {
    INITIALIZATION_ERROR,
    REQUEST_PARSING_ERROR,
    FAIL_BUILD_REQUEST_MODEL,
    ERROR_STUB,
    VALIDATION_ERROR,
    SERVER_ERROR
}