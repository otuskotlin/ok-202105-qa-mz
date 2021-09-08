package ru.otus.opinion.bakend.services

import ru.otus.models.stabs.QuestionStubs
import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State

class QuestionService {

    fun create(ctx: RequestContext): RequestContext {
        ctx.responseQuestion = QuestionStubs.mainQueston
        ctx.state = State.SUCCESS
        return ctx
    }

    fun list(ctx: RequestContext): RequestContext {
        ctx.questions.addAll(QuestionStubs.allQuestions())
        ctx.state = State.SUCCESS
        return ctx
    }
}