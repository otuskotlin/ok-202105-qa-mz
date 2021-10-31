package ru.otus.opinion.transport.mapping

import org.junit.Assert.assertTrue
import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.models.State
import ru.otus.opinion.models.*
import ru.otus.opinion.models.stubs.QuestionStubs
import ru.otus.opinion.openapi.transport.models.CreateQuestionResponse
import ru.otus.opinion.openapi.transport.models.Result
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import ru.otus.opinion.openapi.transport.models.stubs.Stubs as TransportStubs
import ru.otus.opinion.openapi.transport.models.ErrorType as TransportErrorType
import ru.otus.opinion.openapi.transport.models.ErrorLevel as TransportErrorLevel

class MappingTest {

    @Test
    fun transportToContextTest() {
        val query = TransportStubs.createRequestA

        val ctx = RequestContext().setQuery(query)
        assertEquals(RequestContext.RequestType.CREATE, ctx.requestType)
        assertEquals(query.requestId, ctx.requestId.id)

        assertEquals(QuestionStubs.questionA, ctx.requestQuestion)
    }

    @Test
    fun contextToTransportTest() {
        val ctx = RequestContext(
            requestType = RequestContext.RequestType.CREATE,
            requestId = RequestId("123"),
            startTime = Instant.parse("2021-08-07T13:04:11Z"),
            requestQuestion = Question(),
            responseQuestion = QuestionStubs.questionA,
            pagination = Pagination(),
            questions = mutableListOf(),
            errors = mutableListOf(
                ServerError(
                    level = ErrorLevel.ERROR,
                    errorType = ErrorType.ERROR_STUB,
                    message = "Test message."
                )
            ),
            state = State.FAILED
        )
        val response = ctx.toResponse()
        assertTrue(response is CreateQuestionResponse)
        response as CreateQuestionResponse
        assertEquals(ctx.requestId.id, response.requestId)
        assertEquals(Result.ERROR, response.result)
        assertEquals(1, response.errors?.size)
        val expectedErrorMessage = ctx.errors[0].message
        val actualErrorMessage = response.errors?.get(0)?.message
        assertEquals(expectedErrorMessage, actualErrorMessage)
        assertEquals(TransportErrorLevel.ERROR, response.errors?.get(0)?.level)
        assertEquals(TransportErrorType.ERROR_STUB, response.errors?.get(0)?.errorType)
        assertEquals(TransportStubs.questionA, response.question)
    }
}