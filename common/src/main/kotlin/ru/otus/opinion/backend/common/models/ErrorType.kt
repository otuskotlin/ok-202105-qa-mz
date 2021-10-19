package ru.otus.opinion.backend.common.models

enum class ErrorType {
    INITIALIZATION_ERROR,
    REQUEST_PARSING_ERROR,
    STUB_PROCESSING_ERROR,
    VALIDATION_ERROR,
    SERVER_ERROR
}