package ru.otus.opinion.backend.ktor.controllers

import ru.otus.opinion.bakend.services.QuestionService
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.models.ErrorLevel
import ru.otus.opinion.backend.common.models.ServerErrorModel
import ru.otus.opinion.backend.transport.mapping.setQuery
import ru.otus.opinion.backend.transport.mapping.toResponse
import ru.otus.opinion.openapi.models.CreateQuestionRequest
import ru.otus.opinion.openapi.models.QuestionsRequest

class QuestionControllerImpl(private val questionService: QuestionService) : QuestionController {

    override suspend fun create(call: ApplicationCall) {
        val ctx = RequestContext(requestType = RequestContext.RequestType.CREATE)
        try {
            val request = call.receive<CreateQuestionRequest>()
            ctx.setQuery(request)
            questionService.create(ctx)
        } catch (ex: ContentTransformationException) {
            ctx.addError(ServerErrorModel(level = ErrorLevel.ERROR, message = "Wrong json request."))
        } catch (ex: Throwable) {
            ctx.addError(ServerErrorModel(level = ErrorLevel.ERROR, message = "Server error."))
        }
        call.respond(ctx.toResponse())
    }

    override suspend fun list(call: ApplicationCall) {
        val ctx = RequestContext(requestType = RequestContext.RequestType.LIST)
        try {
            val request = call.receive<QuestionsRequest>()
            ctx.setQuery(request)
            questionService.list(ctx)
        } catch (ex: ContentTransformationException) {
            ctx.addError(ServerErrorModel(level = ErrorLevel.ERROR, message = "Wrong json request."))
        } catch (ex: Throwable) {
            ctx.addError(ServerErrorModel(level = ErrorLevel.ERROR, message = "Server error."))
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
