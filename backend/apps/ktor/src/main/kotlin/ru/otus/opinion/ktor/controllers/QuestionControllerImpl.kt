package ru.otus.opinion.ktor.controllers

import com.fasterxml.jackson.core.JsonParseException
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.context.RequestContext.RequestType
import ru.otus.opinion.models.ErrorLevel
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.transport.mapping.setQuery
import ru.otus.opinion.transport.mapping.toResponse
import ru.otus.opinion.bakend.services.QuestionService
import ru.otus.opinion.openapi.transport.models.CreateQuestionRequest
import ru.otus.opinion.openapi.transport.models.QuestionsRequest

class QuestionControllerImpl(private val questionService: QuestionService) : QuestionController {

    override suspend fun create(call: ApplicationCall) = perform(call, RequestType.CREATE) { ctx ->
        val request = call.receive<CreateQuestionRequest>()
        ctx.setQuery(request)
        questionService.create(ctx)
    }

    override suspend fun list(call: ApplicationCall) = perform(call, RequestType.LIST) { ctx ->
        val request = call.receive<QuestionsRequest>()
        ctx.setQuery(request)
        questionService.list(ctx)
    }

    private suspend fun perform(call: ApplicationCall, requestType: RequestType, action: suspend (ctx: RequestContext) -> Unit) {
        val ctx = RequestContext(requestType = requestType)
        try {
            action(ctx)
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
        call.respond(ctx.toResponse())
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
