package ru.otus.opinion.backend.transport.mapping

import org.junit.Assert.assertTrue
import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.models.*
import ru.otus.opinion.openapi.models.CreateQuestionRequest
import ru.otus.opinion.openapi.models.CreateQuestionResponse
import ru.otus.opinion.openapi.models.ProcessingMode
import ru.otus.opinion.openapi.models.Result
import ru.otus.opinion.openapi.models.Visibility
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import ru.otus.opinion.openapi.models.Question as TransportQuestion
import ru.otus.opinion.openapi.models.QuestionState as TransportQuestionState
import ru.otus.opinion.openapi.models.Permission as TransportPermission

class MappingTest {

    @Test
    fun transportToContextTest() {
        val transportQuery = CreateQuestionRequest(
            requestId = "123",
            processingMode = ProcessingMode.STUB,
            question = TransportQuestion(
                questionId = "",
                title = "Ultimate question.",
                content = "What do you get if you multiply six by nine?",
                author = "James Casingworthy",
                language = "eng",
                tags = listOf("philosophy"),
                state = TransportQuestionState.MODERATED,
                visibility = Visibility.PUBLIC
            )
        )
        val ctx = RequestContext().setQuery(transportQuery)
        assertEquals(RequestContext.RequestType.CREATE, ctx.contextType)
        assertEquals(transportQuery.requestId, ctx.requestId)
        val question = transportQuery.question
        val questionModel = ctx.requestQuestion
        assertEquals(question?.questionId, questionModel.questionId)
        assertEquals(question?.title, questionModel.title)
        assertEquals(question?.content, questionModel.content)
        assertEquals(question?.author, questionModel.author.id)
        assertEquals(question?.language, questionModel.language.id)
        assertEquals(1, questionModel.tags.size)
        assertEquals("philosophy", questionModel.tags[0].id)
        assertEquals(QuestionState.MODERATED, questionModel.state)
        assertEquals(QuestionVisibility.PUBLIC, questionModel.visibility)

    }

    @Test
    fun contextToTransportTest() {
        val ctx = RequestContext(
            contextType = RequestContext.RequestType.CREATE,
            requestId = "123",
            startTime = Instant.parse("2021-08-07T13:04:11Z"),
            requestQuestion = Question(),
            responseQuestion = Question(
                questionId = "321",
                title = "Ultimate question.",
                content = "What do you get if you multiply six by nine?",
                author = UserId("James Casingworthy"),
                creationTime = Instant.parse("2021-08-07T13:04:01Z"),
                language = Language("eng"),
                tags = listOf(QuestionTag("philosophy")),
                likesCount = 111,
                answersCount = 1,
                permissions = setOf(Permission.READ, Permission.UPDATE, Permission.DELETE),
                state = QuestionState.ACCEPTED,
                visibility = QuestionVisibility.REGISTERED_ONLY
            ),
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
        val actualQuestion = response.question
        assertEquals("321", actualQuestion?.questionId)
        assertEquals("Ultimate question.", actualQuestion?.title)
        assertEquals("What do you get if you multiply six by nine?", actualQuestion?.content)
        assertEquals("James Casingworthy", actualQuestion?.author)
        assertEquals("2021-08-07T13:04:01Z", actualQuestion?.creationTime)
        assertEquals("eng", actualQuestion?.language)
        assertEquals(1, actualQuestion?.tags?.size)
        assertEquals("philosophy", actualQuestion?.tags?.get(0))
        assertEquals(111, actualQuestion?.likesCount)
        assertEquals(1, actualQuestion?.answersCount)
        assertEquals(setOf(TransportPermission.DELETE, TransportPermission.READ, TransportPermission.UPDATE), actualQuestion?.permissions)
        assertEquals(TransportQuestionState.ACCEPTED, actualQuestion?.state)
        assertEquals(Visibility.REGISTERED_ONLY, actualQuestion?.visibility)
    }
}