package ru.otus.opinion.backend.common.context

import ru.otus.opinion.backend.common.models.Pagination
import ru.otus.opinion.backend.common.models.ProcessingMessage
import ru.otus.opinion.backend.common.models.Question
import java.time.Instant

data class RequestContext (
    var contextType: RequestType = RequestType.NONE,
    var requestId: String = "",
    var startTime: Instant = Instant.now(),
    var requestQuestion: Question = Question(),
    var responseQuestion: Question = Question(),
    var pagination: Pagination = Pagination(),
    var questions: MutableList<Question> = mutableListOf(),
    var processingMessages: MutableList<ProcessingMessage> = mutableListOf(),
    var state: State = State.STARTED
) {
    enum class RequestType {
        NONE, LIST, CREATE
    }
}
