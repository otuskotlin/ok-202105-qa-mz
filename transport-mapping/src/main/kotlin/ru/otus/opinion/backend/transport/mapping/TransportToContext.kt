package ru.otus.opinion.backend.transport.mapping

import ru.otus.opinion.backend.common.context.RequestContext
import ru.otus.opinion.backend.common.models.*
import ru.otus.opinion.openapi.models.CreateQuestionRequest
import ru.otus.opinion.openapi.models.QuestionsRequest
import ru.otus.opinion.openapi.models.Visibility
import java.time.Instant
import java.time.ZonedDateTime
import ru.otus.opinion.openapi.models.Question as QuestionTransport
import ru.otus.opinion.openapi.models.Pagination as PaginationTransport
import ru.otus.opinion.openapi.models.Pagination.Relation as PaginationRelation
import ru.otus.opinion.openapi.models.QuestionState as QuestionStateTransport

fun RequestContext.setQuery(query: CreateQuestionRequest) = apply {
    this.contextType = RequestContext.RequestType.CREATE
    this.requestId = query.requestId ?: ""
    this.requestQuestion = query.question?.toModel() ?: Question()
}

fun RequestContext.setQuery(query: QuestionsRequest) = apply {
    this.contextType = RequestContext.RequestType.LIST
    this.requestId = query.requestId ?: ""
    this.pagination = query.pagination?.toModel() ?: Pagination()
}

private fun QuestionTransport.toModel() : Question {
    return Question (
        questionId = questionId ?: "",
        title = title ?: "",
        content = content ?: "",
        author = author?.let { UserId(it) } ?: UserId.EMPTY,
        creationTime = if (creationTime == null) Instant.now() else ZonedDateTime.parse(creationTime).toInstant(),
        language = language?.let { Language(it) } ?: Language.UNDEFINED,
        tags = tags?.map(::QuestionTag) ?: mutableListOf(),
        likesCount = 0,
        answersCount = 0,
        permissions = mutableSetOf(),
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
    