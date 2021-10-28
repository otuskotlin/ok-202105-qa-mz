package ru.otus.opinion.validation.validators

import ru.otus.opinion.validation.ValidationResult

class StringIsNotEmptyValidator(
    override val field: String = "",
    private val message: String = "String must be not empty."
): BaseValidator<String>() {

    override fun validate(obj: String?): ValidationResult =
        if (obj.isNullOrBlank()) result(message) else ValidationResult.SUCCESS
}