package ru.otus.opinion.bakend.services

import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.logics.Crud

class QuestionServiceImpl(private val crud: Crud) : QuestionService {

    override suspend fun handle(ctx: RequestContext): RequestContext = try {
        when(ctx.requestType) {
            RequestContext.RequestType.NONE -> ctx
            RequestContext.RequestType.LIST -> list(ctx)
            RequestContext.RequestType.CREATE -> create(ctx)
        }
    } catch(ex: Throwable) {
        ctx.addError(ServerError(ex))
    }

    override suspend fun create(ctx: RequestContext): RequestContext {
        crud.create(ctx)
        return ctx
    }

    override suspend fun list(ctx: RequestContext): RequestContext {
        crud.list(ctx)
        return ctx
    }
}