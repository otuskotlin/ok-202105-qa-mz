package ru.otus.opinion.validation

interface Validator<T> {
    val field: String
    fun validate(obj: T?): ValidationResult
}