package ru.otus.opinion.backend.common.context

import ru.otus.opinion.backend.common.models.ErrorLevel
import ru.otus.opinion.backend.common.models.Pagination
import ru.otus.opinion.backend.common.models.Question
import ru.otus.opinion.backend.common.models.ServerError
import java.time.Instant

data class RequestContext (
    var contextType: RequestType = RequestType.NONE,
    var requestId: String = "",
    var startTime: Instant = Instant.now(),
    var requestQuestion: Question = Question(),
    var responseQuestion: Question = Question(),
    var pagination: Pagination = Pagination(),
    var questions: MutableList<Question> = mutableListOf(),
    var errors: MutableList<ServerError> = mutableListOf(),
    var state: State = State.STARTED
) {
    fun addError(error: ServerError) = apply {
        errors.add(error)
        if (error.level == ErrorLevel.ERROR) {
            state = State.FAILED
        }
    }

    enum class RequestType {
        NONE, LIST, CREATE
    }
}
