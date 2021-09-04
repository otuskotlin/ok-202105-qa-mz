package ru.otus.opinion.backend.transport.mapping

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.context.RequestContext.RequestType.*
import ru.otus.opinion.backend.common.context.State
import ru.otus.opinion.backend.common.models.*
import ru.otus.opinion.openapi.models.*
import ru.otus.opinion.openapi.models.Permission as PermissionTransport
import ru.otus.opinion.openapi.models.Question as QuestionTransport
import ru.otus.opinion.openapi.models.QuestionState as QuestionStateTransport

fun RequestContext.toResponse() = when(contextType) {
    LIST -> toListQuestionsResponse()
    CREATE -> toCreateQuestionResponse()
    NONE -> toEmptyResponse()
}

fun RequestContext.toListQuestionsResponse() = QuestionsResponse(
    requestId = requestId,
    result = toResult(state),
    processingInfos = processingMessages.map(ProcessingMessage::toTransport),
    questions = questions.map(Question::toTransport)
)

fun RequestContext.toCreateQuestionResponse() = CreateQuestionResponse (
    requestId = requestId,
    result = toResult(state),
    processingInfos = processingMessages.map(ProcessingMessage::toTransport),
    question = responseQuestion.toTransport()
)

fun RequestContext.toEmptyResponse() = EmptyResponse(
    requestId = requestId,
    result = Result.ERROR,
    processingInfos = processingMessages
        .map(ProcessingMessage::toTransport)
        .plus(RequestProcessingMessage("Request was not processed."))
)

private fun Question.toTransport() = QuestionTransport(
    questionId = questionId,
    title = title,
    content = content,
    author = author,
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

private fun ProcessingMessage.toTransport() = RequestProcessingMessage(message)

private fun toResult(state: State) : Result = when(state) {
    State.SUCCESS -> Result.SUCCESS
    State.STARTED -> Result.ERROR
    State.FAILED -> Result.ERROR
}