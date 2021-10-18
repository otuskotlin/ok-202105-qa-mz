package ru.otus.opinion.backend.ktor.controllers

import com.fasterxml.jackson.core.JsonParseException
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.RequestContext.RequestType
import ru.otus.opinion.backend.common.models.ErrorLevel
import ru.otus.opinion.backend.common.models.ServerErrorModel
import ru.otus.opinion.backend.transport.mapping.setQuery
import ru.otus.opinion.backend.transport.mapping.toResponse
import ru.otus.opinion.bakend.services.QuestionService
import ru.otus.opinion.openapi.models.CreateQuestionRequest
import ru.otus.opinion.openapi.models.QuestionsRequest

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
            ctx.addError(ServerErrorModel(level = ErrorLevel.ERROR, message = "Wrong json in request: ${ex.message}"))
        } catch (ex: Throwable) {
            ctx.addError(ServerErrorModel(level = ErrorLevel.ERROR, message = "Server error: ${ex.message}"))
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
