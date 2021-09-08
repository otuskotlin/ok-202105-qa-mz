package ru.otus.opinion.backend.common.models

interface ServerError {
    val field: String
    val level: ErrorLevel
    val message: String
}