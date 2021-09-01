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
    val transportState = state;
    val modelState : QuestionState =
        if (transportState == null) QuestionState.PROPOSED else QuestionState.valueOf(transportState.name)
    val transportVisibility: Visibility? = visibility
    val modelVisibility : QuestionVisibility =
        if (transportVisibility == null) QuestionVisibility.OWNER_ONLY else QuestionVisibility.valueOf(transportVisibility.name)
    return Question (
        questionId = questionId ?: "",
        title = title ?: "",
        content = content ?: "",
        author = author ?: "",
        creationTime = if (creationTime == null) Instant.now() else ZonedDateTime.parse(creationTime).toInstant(),
        language = language ?: "",
        tags = tags ?: mutableListOf(),
        likesCount = 0,
        answersCount = 0,
        permissions = mutableSetOf(),
        state = modelState,
        visibility = modelVisibility
    )
}

private fun PaginationTransport.toModel() = Pagination (
    count = this.objectsCount ?: 0,
    id = this.objectId ?: "",
    relation = this.relation?.toModel() ?: Relation.BEFORE
)

private fun PaginationRelation.toModel() = Relation.valueOf(value.uppercase())
