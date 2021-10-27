package ru.otus.opinion.validation.validators

import ru.otus.opinion.validation.ValidationError
import ru.otus.opinion.validation.ValidationResult
import ru.otus.opinion.validation.Validator

abstract class BaseValidator<T> : Validator<T>{
    fun result(message: String): ValidationResult = ValidationResult(errors = listOf(ValidationError(message)))
}