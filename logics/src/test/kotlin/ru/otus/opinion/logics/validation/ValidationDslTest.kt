package ru.otus.opinion.logics.validation

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.opinion.backend.common.context.Context
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.cor.dsl.chain
import ru.otus.opinion.backend.common.models.ServerError
import ru.otus.opinion.validation.ValidationResult
import ru.otus.opinion.validation.Validator
import ru.otus.opinion.validation.validators.NotNullValidator
import ru.otus.opinion.validation.validators.RangeValidator
import ru.otus.opinion.validation.validators.StringIsNotEmptyValidator
import kotlin.test.assertEquals

class ValidationDslTest {

    @Test
    fun testDsl() {
        val ctx = TestContext()
        val chain = chain<TestContext> {
            validate { name } with StringIsNotEmptyValidator("name")
            validate { surname } with StringIsNotEmptyValidator("surname")
            validate { text } with ExceptionProducer("text")
            validate { lang } with NotNullValidator("lang")
            validate { age } with RangeValidator(field = "age", min = 1, max = 200)
        }
        runBlocking {
            chain.process(ctx)
        }
        assertEquals(State.FAILED, ctx.state)
        val expectedFields = setOf("name", "text", "lang")
        val actualFields = ctx.errors.map(ServerError::field).toSet()
        assertEquals(expectedFields, actualFields)
    }

    /**
     * Context used to test how validation DSL works.
     */
    class TestContext(
        var name: String = "",
        var surname: String = "Smith",
        var text: String = "Test text.",
        var lang: String? = null,
        var age: Int = 53,
        var errors: MutableList<ServerError> = mutableListOf()
    ) : Context {
        override var state: State = State.SUCCESS
        override fun addError(error: ServerError) = apply { errors.add(error) }
    }

    /**
     * This validator always throws [RuntimeException].
     */
    class ExceptionProducer(
        override val field: String = ""
    ) : Validator<String>
    {
        override fun validate(obj: String?): ValidationResult =
            throw RuntimeException("Test exception.")
    }
}
