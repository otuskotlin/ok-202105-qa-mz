package ru.otus.opinion.logics.workers

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.context.Stub
import ru.otus.opinion.backend.common.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.backend.common.models.ServerError
import ru.otus.opinion.models.stabs.QuestionStubs

internal fun ChainBuilder<RequestContext>.createQuestionStub() = worker {
    on {
        state == State.RUNNING
    }
    execute {
        when (stub) {
            Stub.NONE -> {}
            Stub.SUCCESS -> {
                responseQuestion = requestQuestion.copy(questionId = QuestionStubs.questionId)
                state = State.SUCCESS
            }
            Stub.FAIL -> {
                addError(ServerError(message = QuestionStubs.errorMessage))
                state = State.FAILED
            }
        }
    }
}
