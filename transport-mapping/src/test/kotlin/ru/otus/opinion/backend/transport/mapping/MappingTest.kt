package ru.otus.opinion.backend.transport.mapping

import org.junit.Assert.assertTrue
import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.models.*
import ru.otus.opinion.models.stabs.QuestionStubs
import ru.otus.opinion.openapi.models.CreateQuestionResponse
import ru.otus.opinion.openapi.models.Result
import ru.otus.opinion.openapi.models.Visibility
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import ru.otus.opinion.openapi.models.Permission as TransportPermission
import ru.otus.opinion.openapi.models.QuestionState as TransportQuestionState
import ru.otus.opinion.openapi.stabs.Stubs as TransportStubs

class MappingTest {

    @Test
    fun transportToContextTest() {
        val query = TransportStubs.createRequestA

        val ctx = RequestContext().setQuery(query)
        assertEquals(RequestContext.RequestType.CREATE, ctx.contextType)
        assertEquals(query.requestId, ctx.requestId)

        assertEquals(QuestionStubs.questionA, ctx.requestQuestion)
    }

    @Test
    fun contextToTransportTest() {
        val ctx = RequestContext(
            contextType = RequestContext.RequestType.CREATE,
            requestId = "123",
            startTime = Instant.parse("2021-08-07T13:04:11Z"),
            requestQuestion = Question(),
            responseQuestion = QuestionStubs.questionA,
            pagination = Pagination(),
            questions = mutableListOf(),
            errors = mutableListOf(ServerErrorModel(message = "Test message.")),
            state = State.SUCCESS
        )
        val response = ctx.toResponse()
        assertTrue(response is CreateQuestionResponse)
        response as CreateQuestionResponse
        assertEquals(ctx.requestId, response.requestId)
        assertEquals(Result.SUCCESS, response.result)
        assertEquals(1, response.errors?.size)
        val expectedErrorMessage = ctx.errors[0].message
        val actualErrorMessage = response.errors?.get(0)?.message
        assertEquals(expectedErrorMessage, actualErrorMessage)

        assertEquals(TransportStubs.questionA, response.question)
    }
}