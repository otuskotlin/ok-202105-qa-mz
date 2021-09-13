package ru.otus.opinion.bakend.services

import ru.otus.opinion.models.stabs.QuestionStubs
import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State

class QuestionServiceImpl : QuestionService {

    override fun create(ctx: RequestContext): RequestContext {
        ctx.responseQuestion = QuestionStubs.questionA
        ctx.state = State.SUCCESS
        return ctx
    }

    override fun list(ctx: RequestContext): RequestContext {
        ctx.questions.addAll(QuestionStubs.allQuestions())
        ctx.state = State.SUCCESS
        return ctx
    }
}