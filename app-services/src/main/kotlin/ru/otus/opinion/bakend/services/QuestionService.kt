package ru.otus.opinion.bakend.services

import ru.otus.opinion.backend.common.context.RequestContext

interface QuestionService {
    fun create(ctx: RequestContext): RequestContext
    fun list(ctx: RequestContext): RequestContext
}