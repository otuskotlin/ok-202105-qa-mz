package ru.otus.opinion.validation.validators

import ru.otus.opinion.validation.ValidationResult

class NotNullValidator<T>(
    override val field: String = "",
    private val message: String = "Value of field $field must be not null."
) : BaseValidator<T>() {

    override fun validate(obj: T?): ValidationResult =
        if (obj == null) result(message)  else ValidationResult.SUCCESS
}
