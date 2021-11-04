package ru.otus.opinion.services

import ru.otus.opinion.context.IRequestContext
import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.context.RequestType
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.services.chains.QuestionCreator
import ru.otus.opinion.services.chains.QuestionsListReader

class QuestionServiceImpl(private val config: ServiceConfig) : QuestionService {

    override suspend fun handle(ctx: RequestContext): IRequestContext = try {
        val serviceCtx = ServiceContext(config, ctx)
        when(ctx.requestType) {
            RequestType.LIST -> QuestionsListReader.process(serviceCtx)
            RequestType.CREATE -> QuestionCreator.process(serviceCtx)
            RequestType.NONE -> {}
        }
        ctx
    } catch(ex: Throwable) {
        ctx.addError(ServerError(ex))
    }
}