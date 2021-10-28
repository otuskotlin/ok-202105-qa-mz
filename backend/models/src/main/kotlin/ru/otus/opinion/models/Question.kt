package ru.otus.opinion.models

import java.time.Instant

data class Question (
    var questionId: QuestionId = QuestionId.EMPTY,
    var title: String = "",
    var content: String = "",
    var author: UserId = UserId.EMPTY,
    var creationTime: Instant = Instant.now(),
    var language: Language = Language.UNDEFINED,
    var tags: List<QuestionTag> = mutableListOf(),
    var likesCount: Int = 0,
    var answersCount: Int = 0,
    var permissions: Set<Permission> = mutableSetOf(),
    var state: QuestionState = QuestionState.default,
    var visibility: QuestionVisibility = QuestionVisibility.default
)

