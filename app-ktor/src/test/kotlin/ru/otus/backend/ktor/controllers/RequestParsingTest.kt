package ru.otus.backend.ktor.controllers

import ru.otus.opinion.openapi.models.CreateQuestionResponse
import ru.otus.opinion.openapi.models.ErrorType
import ru.otus.opinion.openapi.models.Result
import ru.otus.opinion.openapi.models.ServerError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RequestParsingTest : RouterTest() {

    @Test
    fun `test invalid json request body`() {
        val invalidRequestBody = "{"
        testPostRequest<CreateQuestionResponse>(invalidRequestBody, "/question/create") {
            assertEquals(Result.ERROR, result)
            assertEquals(1, errors?.size ?: 0)
            val error: ServerError? = errors?.get(0)
            assertNotNull(error)
            val errorType: ErrorType? = error.errorType
            assertEquals(ErrorType.REQUEST_PARSING_ERROR, errorType)
        }
    }
}