package ru.otus.opinion.bakend.services

import ru.otus.opinion.context.RequestContext

interface QuestionService {
    suspend fun handle(ctx: RequestContext): RequestContext
    suspend fun create(ctx: RequestContext): RequestContext
    suspend fun list(ctx: RequestContext): RequestContext
}