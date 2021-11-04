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
import ru.otus.opinion.transport.mapping.setQuery
import ru.otus.opinion.transport.mapping.toResponse
import ru.otus.opinion.services.QuestionService
import ru.otus.opinion.openapi.transport.models.CreateQuestionRequest
import ru.otus.opinion.openapi.transport.models.QuestionsRequest
import ru.otus.opinion.openapi.transport.models.Request

class QuestionControllerImpl(private val questionService: QuestionService) : QuestionController {

    override suspend fun create(call: ApplicationCall) = perform(call, RequestType.CREATE) {
        call.receive<CreateQuestionRequest>()
    }

    override suspend fun list(call: ApplicationCall) = perform(call, RequestType.LIST) {
        call.receive<QuestionsRequest>()
    }

    private suspend fun perform(call: ApplicationCall,
                                requestType: RequestType,
                                parse: suspend () -> Request) {
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
