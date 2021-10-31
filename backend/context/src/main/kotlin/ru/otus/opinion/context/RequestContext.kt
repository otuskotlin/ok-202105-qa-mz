package ru.otus.opinion.context

import ru.otus.opinion.models.*
import java.time.Instant

data class RequestContext (

    /** Repos */


    /** Models */
    var requestType: RequestType = RequestType.NONE,
    var processingMode: ProcessingMode = ProcessingMode.PROD,
    var stub: Stub = Stub.NONE,
    var requestId: RequestId = RequestId.EMPTY,
    var startTime: Instant = Instant.now(),
    var requestQuestion: Question = Question(),
    var responseQuestion: Question = Question(),
    var pagination: Pagination = Pagination(),
    var questions: MutableList<Question> = mutableListOf(),
    var errors: MutableList<ServerError> = mutableListOf(),
    override var state: State = State.INITIAL

) : Context {
    override fun addError(error: ServerError) = apply {
        errors.add(error)
        if (error.level == ErrorLevel.ERROR) {
            state = State.FAILED
        }
    }

    enum class RequestType {
        NONE, LIST, CREATE
    }
}
