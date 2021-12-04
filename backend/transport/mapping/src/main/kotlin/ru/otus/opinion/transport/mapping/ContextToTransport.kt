package ru.otus.opinion.transport.mapping

import ru.otus.opinion.context.IRequestContext
import ru.otus.opinion.context.RequestType.*
import ru.otus.opinion.models.*
import ru.otus.opinion.models.ErrorLevel
import ru.otus.opinion.openapi.transport.models.*
import ru.otus.opinion.openapi.transport.models.ErrorLevel as TransportErrorLevel
import ru.otus.opinion.openapi.transport.models.ErrorType as TransportErrorType
import ru.otus.opinion.openapi.transport.models.Permission as PermissionTransport
import ru.otus.opinion.openapi.transport.models.Question as QuestionTransport
import ru.otus.opinion.openapi.transport.models.QuestionState as QuestionStateTransport
import ru.otus.opinion.openapi.transport.models.ServerError as TransportError


fun IRequestContext.toResponse(): Response = when(requestType) {
    LIST -> toListQuestionsResponse()
    CREATE -> toCreateQuestionResponse()
    NONE -> toEmptyResponse()
}

fun IRequestContext.toListQuestionsResponse() = QuestionsResponse(
    requestId = requestId.id,
    result = toResult(state),
    errors = errors.map(ServerError::toTransport),
    questions = questions.map(Question::toTransport)
)

fun IRequestContext.toCreateQuestionResponse() = CreateQuestionResponse (
    requestId = requestId.id,
    result = toResult(state),
    errors = errors.map(ServerError::toTransport),
    question = responseQuestion.toTransport()
)

fun IRequestContext.toEmptyResponse() : EmptyResponse {
    errors.add(ServerError(level = ErrorLevel.ERROR, message = "Failed to process request."))
    return EmptyResponse(
        requestId = requestId.id,
        result = Result.ERROR,
        errors = errors.map(ServerError::toTransport)
    )
}
private fun Question.toTransport() = QuestionTransport(
    questionId = questionId.id,
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
    ErrorType.FAIL_BUILD_REQUEST_MODEL -> TransportErrorType.FAIL_BUILD_REQUEST_MODEL
    ErrorType.DB_ERROR -> TransportErrorType.DB_ERROR
}

private fun toResult(state: State) : Result = when(state) {
    State.INITIAL -> Result.ERROR
    State.SUCCESS -> Result.SUCCESS
    State.RUNNING -> Result.ERROR
    State.FAILED -> Result.ERROR
}