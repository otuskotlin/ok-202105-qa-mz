package ru.otus.opinion.logics

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.opinion.backend.common.context.ProcessingMode
import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.context.Stub
import ru.otus.opinion.backend.common.models.*
import ru.otus.opinion.models.stabs.QuestionStubs
import kotlin.test.assertEquals

class CrudTest {

    private val crud = Crud()

    @Test
    fun `test request with wrong type`() {
        val ctx = RequestContext(
            requestType = RequestContext.RequestType.LIST
        )
        runBlocking {
            crud.create(ctx)
        }
        assertEquals(State.FAILED, ctx.state)
        val error = ctx.errors[0]
        assertEquals(ErrorType.INITIALIZATION_ERROR, error.errorType)
    }

    @Test
    fun `test create request processing with SUCCESS stub`() {
        val ctx = RequestContext(
            requestType = RequestContext.RequestType.CREATE,
            processingMode = ProcessingMode.STUB,
            stub = Stub.SUCCESS,
            requestId = "123",
            requestQuestion = QuestionStubs.questionA
        )
        runBlocking {
            crud.create(ctx)
        }
        assertEquals(State.SUCCESS, ctx.state)
        assertEquals(0, ctx.errors.size)
        assertEquals(QuestionStubs.questionA, ctx.responseQuestion)
    }

    @Test
    fun `test create request processing with FAIL stub`() {
        val ctx = RequestContext(
            requestType = RequestContext.RequestType.CREATE,
            processingMode = ProcessingMode.STUB,
            stub = Stub.FAIL,
            requestId = "123",
            requestQuestion = QuestionStubs.questionA
        )
        runBlocking {
            crud.create(ctx)
        }
        assertEquals(State.FAILED, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals(ErrorType.ERROR_STUB, ctx.errors[0].errorType)
        assertEquals(QuestionStubs.errorMessage, ctx.errors[0].message)
    }

    @Test
    fun `test questions list request with SUCCESS stub`() {
        val ctx = RequestContext(
            requestType = RequestContext.RequestType.LIST,
            processingMode = ProcessingMode.STUB,
            stub = Stub.SUCCESS,
            requestId = "123",
            pagination = Pagination(count = 2, id = "0", relation = Relation.AFTER)
        )
        runBlocking {
            crud.list(ctx)
        }
        assertEquals(State.SUCCESS, ctx.state)
        assertEquals(0, ctx.errors.size)
        assertEquals(QuestionStubs.allQuestions(), ctx.questions)
    }

    @Test
    fun `test validation of invalid create request`() {
        val ctx = RequestContext(
            requestType = RequestContext.RequestType.CREATE,
            processingMode = ProcessingMode.TEST,
            requestQuestion = Question(title = "") // title string must be not blank
        )
        runBlocking {
            crud.create(ctx)
        }
        assertEquals(State.FAILED, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals(ErrorType.VALIDATION_ERROR, ctx.errors[0].errorType)
    }
}