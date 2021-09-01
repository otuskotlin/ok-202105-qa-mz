package ru.otus.opinion.backend.common.models

import java.time.Instant

data class Question (
    var questionId: String = "",
    var title: String = "",
    var content: String = "",
    var author: String = "",
    var creationTime: Instant = Instant.now(),
    var language: String = "",
    var tags: List<String> = mutableListOf(),
    var likesCount: Int = 0,
    var answersCount: Int = 0,
    var permissions: Set<Permission> = mutableSetOf(),
    var state: QuestionState = QuestionState.default,
    var visibility: QuestionVisibility = QuestionVisibility.default
)

