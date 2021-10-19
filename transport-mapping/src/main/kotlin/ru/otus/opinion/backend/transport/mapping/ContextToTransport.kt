package ru.otus.opinion.backend.transport.mapping

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.RequestContext.RequestType.*
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.models.*
import ru.otus.opinion.backend.common.models.ErrorLevel
import ru.otus.opinion.openapi.models.*
import ru.otus.opinion.openapi.models.Permission as PermissionTransport
import ru.otus.opinion.openapi.models.Question as QuestionTransport
import ru.otus.opinion.openapi.models.QuestionState as QuestionStateTransport
import ru.otus.opinion.openapi.models.ServerError as TransportError
import ru.otus.opinion.openapi.models.ErrorLevel as TransportErrorLevel
import ru.otus.opinion.openapi.models.ErrorType as TransportErrorType


fun RequestContext.toResponse() = when(requestType) {
    LIST -> toListQuestionsResponse()
    CREATE -> toCreateQuestionResponse()
    NONE -> toEmptyResponse()
}

fun RequestContext.toListQuestionsResponse() = QuestionsResponse(
    requestId = requestId,
    result = toResult(state),
    errors = errors.map(ServerError::toTransport),
    questions = questions.map(Question::toTransport)
)

fun RequestContext.toCreateQuestionResponse() = CreateQuestionResponse (
    requestId = requestId,
    result = toResult(state),
    errors = errors.map(ServerError::toTransport),
    question = responseQuestion.toTransport()
)

fun RequestContext.toEmptyResponse() : EmptyResponse {
    errors.add(ServerError(level = ErrorLevel.ERROR, message = "Failed to process request."))
    return EmptyResponse(
        requestId = requestId,
        result = Result.ERROR,
        errors = errors.map(ServerError::toTransport)
    )
}
private fun Question.toTransport() = QuestionTransport(
    questionId = questionId,
    title = title,
    content = content,
    author = author.id,
    creationTime = creationTime.toString(),
    language = language.id,
    tags = tags.map(QuestionTag::id),
    likesCount = likesCount,
    answersCount = answersCount,
    permissions = permissions.map(Permission::toTransport).toSet(),
    state = state.toTransport(),
    visibility = visibility.toTransport()
)

private fun Permission.toTransport() = when(this) {
    Permission.READ -> PermissionTransport.READ
    Permission.UPDATE -> PermissionTransport.UPDATE
    Permission.DELETE -> PermissionTransport.DELETE
}

private fun QuestionState.toTransport() = when(this) {
    QuestionState.PROPOSED -> QuestionStateTransport.PROPOSED
    QuestionState.MODERATED -> QuestionStateTransport.MODERATED
    QuestionState.ACCEPTED -> QuestionStateTransport.ACCEPTED
    QuestionState.OPENED -> QuestionStateTransport.OPENED
    QuestionState.CLOSED -> QuestionStateTransport.CLOSED
}

private fun QuestionVisibility.toTransport() = when(this) {
    QuestionVisibility.OWNER_ONLY -> Visibility.OWNER_ONLY
    QuestionVisibility.REGISTERED_ONLY -> Visibility.REGISTERED_ONLY
    QuestionVisibility.PUBLIC -> Visibility.PUBLIC
}

private fun ServerError.toTransport() = TransportError(
    message = message,
    level = level.toTransport(),
    field = field,
    errorType = errorType.toTransport()
)

private fun ErrorLevel.toTransport(): TransportErrorLevel = when(this) {
    ErrorLevel.HINT -> TransportErrorLevel.HINT
    ErrorLevel.WARNING -> TransportErrorLevel.WARNING
    ErrorLevel.ERROR -> TransportErrorLevel.ERROR
}

private fun ErrorType.toTransport(): TransportErrorType = when(this) {
    ErrorType.INITIALIZATION_ERROR -> TransportErrorType.INITIALIZATION_ERROR
    ErrorType.REQUEST_PARSING_ERROR -> TransportErrorType.REQUEST_PARSING_ERROR
    ErrorType.ERROR_STUB -> TransportErrorType.ERROR_STUB
    ErrorType.VALIDATION_ERROR -> TransportErrorType.VALIDATION_ERROR
    ErrorType.SERVER_ERROR -> TransportErrorType.SERVER_ERROR
}

private fun toResult(state: State) : Result = when(state) {
    State.INITIAL -> Result.ERROR
    State.SUCCESS -> Result.SUCCESS
    State.RUNNING -> Result.ERROR
    State.FAILED -> Result.ERROR
}