package ru.otus.opinion.services.workers

import ru.otus.opinion.cor.dsl.builders.ChainBuilder
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.models.State
import ru.otus.opinion.models.Stub
import ru.otus.opinion.models.stubs.QuestionStubs
import ru.otus.opinion.services.ServiceContext

internal fun ChainBuilder<ServiceContext>.createQuestionStub() = worker {
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
                addError(ServerError(errorType = ErrorType.ERROR_STUB, message = QuestionStubs.errorMessage))
                state = State.FAILED
            }
        }
    }
}
