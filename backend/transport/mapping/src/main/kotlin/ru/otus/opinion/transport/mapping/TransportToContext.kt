package ru.otus.opinion.transport.mapping

import ru.otus.opinion.models.ProcessingMode
import ru.otus.opinion.context.RequestContext
import ru.otus.opinion.models.Stub
import ru.otus.opinion.models.*
import ru.otus.opinion.openapi.transport.models.CreateQuestionRequest
import ru.otus.opinion.openapi.transport.models.QuestionsRequest
import ru.otus.opinion.openapi.transport.models.Request
import ru.otus.opinion.openapi.transport.models.Visibility
import java.time.Instant
import java.time.ZonedDateTime
import ru.otus.opinion.openapi.transport.models.Pagination as PaginationTransport
import ru.otus.opinion.openapi.transport.models.Pagination.Relation as PaginationRelation
import ru.otus.opinion.openapi.transport.models.Permission as TransportPermission
import ru.otus.opinion.openapi.transport.models.ProcessingMode as TransportProcessingMode
import ru.otus.opinion.openapi.transport.models.Question as QuestionTransport
import ru.otus.opinion.openapi.transport.models.QuestionState as QuestionStateTransport
import ru.otus.opinion.openapi.transport.models.Stub as TransportStub

fun RequestContext.setQuery(request: Request): RequestContext = apply {

    fun failedToConvert(): RequestContext {
        return addError(ServerError(
            message = "Failed map request $request to inner model.",
            level = ErrorLevel.ERROR,
            errorType = ErrorType.FAIL_BUILD_REQUEST_MODEL))
    }

    try {
        when(request) {
            is CreateQuestionRequest -> setQuery(request)
            is QuestionsRequest -> setQuery(request)
            else -> failedToConvert()
        }
    } catch (ex: Throwable) {
        failedToConvert()
    }
}

fun RequestContext.setQuery(query: CreateQuestionRequest) = apply {
    setBaseQuery(query)
    requestType = RequestContext.RequestType.CREATE
    requestQuestion = query.question?.toModel() ?: Question()
}

fun RequestContext.setQuery(query: QuestionsRequest) = apply {
    setBaseQuery(query)
    requestType = RequestContext.RequestType.LIST
    pagination = query.pagination?.toModel() ?: Pagination()
}

fun RequestContext.setBaseQuery(query: Request) = apply {
    requestId = RequestId(query.requestId ?: "")
    processingMode = toModel(query.processingMode)
    stub = toModel(query.stub)
}

private fun QuestionTransport.toModel() : Question {
    return Question (
        questionId = QuestionId(questionId ?: ""),
        title = title ?: "",
        content = content ?: "",
        author = author?.let { UserId(it) } ?: UserId.EMPTY,
        creationTime = if (creationTime == null) Instant.now() else ZonedDateTime.parse(creationTime).toInstant(),
        language = language?.let { Language(it) } ?: Language.UNDEFINED,
        tags = tags?.map(::QuestionTag) ?: mutableListOf(),
        likesCount = likesCount ?: 0,
        answersCount = answersCount ?: 0,
        permissions = permissions?.map(::toModel)?.toSet() ?: mutableSetOf(),
        state = toModel(state),
        visibility = toModel(visibility)
    )
}

private fun PaginationTransport.toModel() = Pagination (
    count = this.objectsCount ?: 0,
    id = this.objectId ?: "",
    relation = this.relation?.toModel() ?: Relation.BEFORE
)

private fun PaginationRelation.toModel() = Relation.valueOf(value.uppercase())

private fun toModel(state : QuestionStateTransport?) : QuestionState = when(state) {
    QuestionStateTransport.PROPOSED -> QuestionState.PROPOSED
    QuestionStateTransport.MODERATED -> QuestionState.MODERATED
    QuestionStateTransport.ACCEPTED -> QuestionState.ACCEPTED
    QuestionStateTransport.OPENED -> QuestionState.OPENED
    QuestionStateTransport.CLOSED -> QuestionState.CLOSED
    null -> QuestionState.default
}

private fun toModel(visibility : Visibility?) : QuestionVisibility = when(visibility) {
    Visibility.OWNER_ONLY -> QuestionVisibility.OWNER_ONLY
    Visibility.REGISTERED_ONLY -> QuestionVisibility.REGISTERED_ONLY
    Visibility.PUBLIC -> QuestionVisibility.PUBLIC
    null -> QuestionVisibility.default
}

private fun toModel(permission: TransportPermission?): Permission = when(permission) {
    TransportPermission.READ -> Permission.READ
    TransportPermission.UPDATE -> Permission.UPDATE
    TransportPermission.DELETE -> Permission.DELETE
    null -> Permission.default
}


fun toModel(mode: TransportProcessingMode?): ProcessingMode = when(mode) {
    TransportProcessingMode.PROD -> ProcessingMode.PROD
    TransportProcessingMode.TEST -> ProcessingMode.TEST
    TransportProcessingMode.STUB -> ProcessingMode.STUB
    null -> ProcessingMode.PROD
}

fun toModel(stub: TransportStub?): Stub = when(stub) {
    TransportStub.SUCCESS -> Stub.SUCCESS
    TransportStub.FAIL -> Stub.FAIL
    null -> Stub.NONE
}
