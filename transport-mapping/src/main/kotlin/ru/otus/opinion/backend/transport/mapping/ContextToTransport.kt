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
    NONE -> toEmptyResponse("Request was not processed.")
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

fun RequestContext.toEmptyResponse(s: String) = EmptyResponse(
    requestId = requestId,
    result = Result.ERROR,
    processingInfos = processingMessages.map(ProcessingMessage::toTransport)
)

private fun Question.toTransport() = QuestionTransport(
    questionId = questionId,
    title = title,
    content = content,
    author = author,
    creationTime = creationTime.toString(),
    language = language,
    tags = tags,
    likesCount = likesCount,
    answersCount = answersCount,
    permissions = permissions.map(Permission::toTransport).toSet(),
    state = state.toTransport(),
    visibility = visibility.toTransport()
)

private fun Permission.toTransport() = PermissionTransport.valueOf(name)

private fun QuestionState.toTransport() = QuestionStateTransport.valueOf(name)

private fun QuestionVisibility.toTransport() = Visibility.valueOf(name)

private fun ProcessingMessage.toTransport() = RequestProcessingMessage(message)

private fun toResult(state: State) : Result = when(state) {
    State.SUCCESS -> Result.SUCCESS
    State.STARTED -> Result.ERROR
    State.FAILED -> Result.ERROR
}