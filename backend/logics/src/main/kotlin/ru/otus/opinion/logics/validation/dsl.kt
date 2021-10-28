package ru.otus.opinion.logics.validation

import ru.otus.opinion.context.Context
import ru.otus.opinion.context.State
import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.models.ErrorLevel
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.validation.Validator

/**
 * Validation DSL, may be used inside the CoR DSL [ChainBuilder].
 * Usage pattern:
 * chain {
 *     validate { <lambda: context -> validated object> } with <validator>
 * }
 * View example in ValidationDslTest
 *
 * TODO: how to make link to the ValidationDslTest class? [] - does not work
 */
infix fun <CTX : Context, T> ChainBuilder<CTX>
        .validate(validatedObjectExtractor: CTX.() -> T?) =
    ValidationTaskBuilder(this, validatedObjectExtractor)

class ValidationTaskBuilder<CTX : Context, T>(
    private val chainBuilder: ChainBuilder<CTX>,
    private val validatedObjectExtractor: CTX.() -> T?) {

    infix fun with(validator: Validator<T>) {
        chainBuilder.worker {
            execute {
                val validatedObject: T? = validatedObjectExtractor()
                val validationResult = validator.validate(validatedObject)
                if (!validationResult.isSuccess) {
                    state = State.FAILED
                }
                validationResult.errors.forEach { error ->
                    addError(
                        ServerError(
                            field = validator.field,
                            message = error.message,
                            level = ErrorLevel.ERROR,
                            errorType = ErrorType.VALIDATION_ERROR
                        )
                    )
                }
            }
            onException {
                state = State.FAILED
                addError(
                    ServerError(
                        field = validator.field,
                        message = "Failed to validate request. Caught an exception: $it",
                        level = ErrorLevel.ERROR,
                        errorType = ErrorType.SERVER_ERROR
                    ))
            }
        }
    }
}
