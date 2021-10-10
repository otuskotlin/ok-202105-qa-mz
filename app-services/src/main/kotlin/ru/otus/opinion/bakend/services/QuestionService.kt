package ru.otus.opinion.bakend.services

import ru.otus.opinion.backend.common.context.RequestContext

interface QuestionService {
    suspend fun create(ctx: RequestContext): RequestContext
    suspend fun list(ctx: RequestContext): RequestContext
}