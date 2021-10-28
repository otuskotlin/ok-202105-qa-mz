package ru.otus.backend.ktor.controllers

import ru.otus.opinion.openapi.transport.models.CreateQuestionResponse
import ru.otus.opinion.openapi.transport.models.QuestionsResponse
import ru.otus.opinion.openapi.transport.models.Result
import ru.otus.opinion.openapi.transport.models.stubs.Stubs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuestionRouteTest : RouterTest() {


    @Test
    fun testCreate() {
        val body = Stubs.createRequestA
        testPostRequest<CreateQuestionResponse>(body, "/question/create") {
            assertEquals(Result.SUCCESS, result)
            assertTrue(errors.isNullOrEmpty())
            assertEquals(Stubs.questionA, question)
        }
    }

    @Test
    fun testList() {
        val body = Stubs.questionsRequest
        testPostRequest<QuestionsResponse>(body, "question/list") {
            assertEquals(Result.SUCCESS, result)
            assertTrue(errors.isNullOrEmpty())
            assertEquals(body.requestId, requestId)
            assertEquals(Stubs.allQuestions, questions)
        }
    }

}