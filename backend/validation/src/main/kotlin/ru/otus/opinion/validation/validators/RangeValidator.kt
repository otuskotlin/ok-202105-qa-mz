package ru.otus.opinion.validation.validators

import ru.otus.opinion.validation.*

class RangeValidator<T : Comparable<T>?>(
    override val field: String = "",
    private val min: T? = null,
    private val max: T? = null
) : BaseValidator<T>() {

    override fun validate(obj: T?): ValidationResult = when {
        obj == null -> result("Value of field $field must be not null.")
        (min != null && min > obj) || (max != null &&  max < obj) ->
            result("Value $obj of field $field is out of range [$min, $max]")
        else -> ValidationResult.SUCCESS
    }
}