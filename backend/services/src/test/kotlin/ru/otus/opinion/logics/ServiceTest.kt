package ru.otus.opinion.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.context.RequestType
import ru.otus.opinion.models.*
import ru.otus.opinion.models.stubs.QuestionStubs
import ru.otus.opinion.repo.inmemory.CacheBasedRepo
import ru.otus.opinion.services.QuestionServiceImpl
import ru.otus.opinion.services.ServiceConfig
import kotlin.test.assertEquals

class ServiceTest {
    private val config = ServiceConfig(prodRepo = CacheBasedRepo(), testRepo = CacheBasedRepo())
    private val service = QuestionServiceImpl(config)

    @Test
    fun `test request with wrong type`() {
        val ctx = RequestContext(
            requestType = RequestType.LIST,
            state = State.SUCCESS
        )
        runBlocking {
            service.handle(ctx)
        }
        assertEquals(State.FAILED, ctx.state)
        val error = ctx.errors[0]
        assertEquals(ErrorType.INITIALIZATION_ERROR, error.errorType)
    }

    @Test
    fun `test create request processing with SUCCESS stub`() {
        val ctx = RequestContext(
            requestType = RequestType.CREATE,
            processingMode = ProcessingMode.STUB,
            stub = Stub.SUCCESS,
            requestId = RequestId("123"),
            requestQuestion = QuestionStubs.questionA
        )
        runBlocking {
            service.handle(ctx)
        }
        assertEquals(State.SUCCESS, ctx.state)
        assertEquals(0, ctx.errors.size)
        assertEquals(QuestionStubs.questionA, ctx.responseQuestion)
    }

    @Test
    fun `test create request processing with FAIL stub`() {
        val ctx = RequestContext(
            requestType = RequestType.CREATE,
            processingMode = ProcessingMode.STUB,
            stub = Stub.FAIL,
            requestId = RequestId("123"),
            requestQuestion = QuestionStubs.questionA
        )
        runBlocking {
            service.handle(ctx)
        }
        assertEquals(State.FAILED, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals(ErrorType.ERROR_STUB, ctx.errors[0].errorType)
        assertEquals(QuestionStubs.errorMessage, ctx.errors[0].message)
    }

    @Test
    fun `test questions list request with SUCCESS stub`() {
        val ctx = RequestContext(
            requestType = RequestType.LIST,
            processingMode = ProcessingMode.STUB,
            stub = Stub.SUCCESS,
            requestId = RequestId("123"),
            pagination = Pagination(count = 2, id = "0", relation = Relation.AFTER)
        )
        runBlocking {
            service.handle(ctx)
        }
        assertEquals(State.SUCCESS, ctx.state)
        assertEquals(0, ctx.errors.size)
        assertEquals(QuestionStubs.allQuestions(), ctx.questions)
    }

    @Test
    fun `test validation of invalid create request`() {
        val ctx = RequestContext(
            requestType = RequestType.CREATE,
            processingMode = ProcessingMode.TEST,
            requestQuestion = Question(title = "") // title string must be not blank
        )
        runBlocking {
            service.handle(ctx)
        }
        assertEquals(State.FAILED, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals(ErrorType.VALIDATION_ERROR, ctx.errors[0].errorType)
    }
}