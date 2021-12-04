package ru.otus.opinion.ktor.controllers

import com.fasterxml.jackson.core.JsonParseException
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.context.RequestType
import ru.otus.opinion.models.ErrorLevel
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.openapi.transport.models.*
import ru.otus.opinion.services.QuestionService
import ru.otus.opinion.transport.mapping.setQuery
import ru.otus.opinion.transport.mapping.toListQuestionsResponse
import ru.otus.opinion.transport.mapping.toResponse
import java.util.*

class QuestionControllerImpl(private val questionService: QuestionService) : QuestionController {

    override suspend fun defaultQuestions(): List<Question> {
        val requestId = UUID.randomUUID().toString()
        val pagination = Pagination(objectsCount = 10, objectId = "", relation = Pagination.Relation.AFTER)
        val landingPageRequest = QuestionsRequest(
            requestId = requestId,
            processingMode = ProcessingMode.PROD,
            pagination = pagination
        )
        val ctx = perform(RequestType.LIST) { landingPageRequest }
        return ctx.toListQuestionsResponse().questions ?: emptyList()
    }

    override suspend fun create(call: ApplicationCall) {
        val ctx = perform(RequestType.CREATE) { call.receive<CreateQuestionRequest>() }
        call.respond(ctx.toResponse())
    }

    override suspend fun list(call: ApplicationCall) {
        val ctx = perform(RequestType.LIST) { call.receive<QuestionsRequest>()}
        call.respond(ctx.toResponse())
    }

    private suspend fun perform(requestType: RequestType,
                                parse: suspend () -> Request): RequestContext {
        val ctx = RequestContext(requestType = requestType)
        try {
            val request : Request = parse()
            ctx.setQuery(request)
            questionService.handle(ctx)
        } catch (ex: JsonParseException) {
            ctx.addError(
                ServerError(
                    level = ErrorLevel.ERROR,
                    errorType = ErrorType.REQUEST_PARSING_ERROR,
                    message = "Wrong json in request: ${ex.message}",
                )
            )
        } catch (ex: Throwable) {
            ctx.addError(ServerError(ex))
        }
        return ctx
    }
}

/**
 * Instead of controller class can use ApplicationCall extensions.
 */

/**
suspend fun ApplicationCall.create(questionService: QuestionService) {
val request = receive<CreateQuestionRequest>()
val ctx = RequestContext().setQuery(request)
respond(questionService.create(ctx).toCreateQuestionResponse())
}
 */
