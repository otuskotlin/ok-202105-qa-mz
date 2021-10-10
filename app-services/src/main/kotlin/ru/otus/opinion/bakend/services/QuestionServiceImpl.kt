package ru.otus.opinion.bakend.services

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.logics.Crud

class QuestionServiceImpl(private val crud: Crud) : QuestionService {

    override suspend fun create(ctx: RequestContext): RequestContext {
        crud.create(ctx)
        return ctx
    }

    override suspend fun list(ctx: RequestContext): RequestContext {
        crud.list(ctx)
        return ctx
    }
}