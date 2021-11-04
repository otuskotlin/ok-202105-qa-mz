package ru.otus.opinion.context

import ru.otus.opinion.models.*
import java.time.Instant

interface IRequestContext : Context {
    var requestType: RequestType
    var processingMode: ProcessingMode
    var stub: Stub
    var requestId: RequestId
    var startTime: Instant
    var requestQuestion: Question
    var responseQuestion: Question
    var pagination: Pagination
    var questions: MutableList<Question>
    var errors: MutableList<ServerError>
    override var state: State
    override fun addError(error: ServerError): RequestContext
}