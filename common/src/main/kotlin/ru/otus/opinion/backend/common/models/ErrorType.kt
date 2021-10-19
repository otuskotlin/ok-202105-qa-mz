package ru.otus.opinion.backend.common.models

enum class ErrorType {
    INITIALIZATION_ERROR,
    REQUEST_PARSING_ERROR,
    ERROR_STUB,
    VALIDATION_ERROR,
    SERVER_ERROR
}