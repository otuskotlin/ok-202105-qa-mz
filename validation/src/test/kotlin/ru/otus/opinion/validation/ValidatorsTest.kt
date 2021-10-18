package ru.otus.opinion.validation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.otus.opinion.validation.validators.NotNullValidator
import ru.otus.opinion.validation.validators.RangeValidator
import ru.otus.opinion.validation.validators.StringIsNotEmptyValidator

class ValidatorsTest {

    @Test
    fun testNotNullValidator() {
        val validator = NotNullValidator<String>()

        var result = validator.validate("")
        assertTrue(result.isSuccess)

        result = validator.validate(null)
        assertFalse(result.isSuccess)
    }

    @Test
    fun testStringIsNotEmptyValidator() {
        val validator = StringIsNotEmptyValidator()

        var result = validator.validate(null)
        assertFalse(result.isSuccess)

        result = validator.validate("   ")
        assertFalse(result.isSuccess)

        result = validator.validate("Hello!")
        assertTrue(result.isSuccess)
    }

    @Test
    fun testRangeValidator() {
        var validator = RangeValidator(min = 1, max = 10)

        var result = validator.validate(null)
        assertFalse(result.isSuccess)

        result = validator.validate(0)
        assertFalse(result.isSuccess)

        result = validator.validate(11)
        assertFalse(result.isSuccess)

        result = validator.validate(5)
        assertTrue(result.isSuccess)

        validator = RangeValidator(max = 10)

        result = validator.validate(null)
        assertFalse(result.isSuccess)

        result = validator.validate(0)
        assertTrue(result.isSuccess)

        result = validator.validate(11)
        assertFalse(result.isSuccess)

    }
}