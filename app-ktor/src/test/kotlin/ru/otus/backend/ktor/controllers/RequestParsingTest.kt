package ru.otus.backend.ktor.controllers

import ru.otus.opinion.openapi.models.CreateQuestionResponse
import ru.otus.opinion.openapi.models.Result
import kotlin.test.Test
import kotlin.test.assertEquals

class RequestParsingTest : RouterTest() {

    @Test
    fun `test invalid json request body`() {
        val invalidRequestBody = "{"
        testPostRequest<CreateQuestionResponse>(invalidRequestBody, "/question/create") {
            assertEquals(Result.ERROR, result)
            assertEquals(1, errors?.size ?: 0)
        }
    }
}