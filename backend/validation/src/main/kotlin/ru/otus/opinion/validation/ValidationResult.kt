package ru.otus.opinion.validation

class ValidationResult(val errors: List<ValidationError>) {
    val isSuccess: Boolean
        get() = errors.isEmpty()

    companion object {
        val SUCCESS = ValidationResult(emptyList())
    }
}

data class ValidationError(val message: String)