package ru.otus.opinion.context

import ru.otus.opinion.models.*
import java.time.Instant

data class RequestContext (
    override var requestType: RequestType = RequestType.NONE,
    override var processingMode: ProcessingMode = ProcessingMode.PROD,
    override var stub: Stub = Stub.NONE,
    override var requestId: RequestId = RequestId.EMPTY,
    override var startTime: Instant = Instant.now(),
    override var requestQuestion: Question = Question(),
    override var responseQuestion: Question = Question(),
    override var pagination: Pagination = Pagination(),
    override var questions: MutableList<Question> = mutableListOf(),
    override var errors: MutableList<ServerError> = mutableListOf(),
    override var state: State = State.INITIAL

) : IRequestContext {
    override fun addError(error: ServerError) = apply {
        errors.add(error)
        if (error.level == ErrorLevel.ERROR) {
            state = State.FAILED
        }
    }
}
