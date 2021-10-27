package ru.otus.opinion.models.stabs

import ru.otus.opinion.backend.common.models.*
import java.time.Instant

object QuestionStubs {

    val questionId = QuestionId("Stub question id")

    val questionA = Question(
        questionId = questionId,
        title = "Ultimate question.",
        content = "What do you get if you multiply six by nine?",
        author = UserId("James Casingworthy"),
        creationTime = Instant.parse("2021-09-13T19:11:07Z"),
        language = Language("eng"),
        tags = listOf(QuestionTag("philosophy")),
        likesCount = 111,
        answersCount = 1,
        permissions = setOf(Permission.READ, Permission.UPDATE, Permission.DELETE),
        state = QuestionState.MODERATED,
        visibility = QuestionVisibility.PUBLIC
    )

    val questionB = Question(
        questionId = QuestionId("17"),
        title = "Funny question.",
        content = "To beat or not to beat?",
        author = UserId("Mary"),
        creationTime = Instant.parse("2021-08-07T14:04:01Z"),
        language = Language("eng"),
        tags = listOf(QuestionTag("humor")),
        likesCount = 117,
        answersCount = 3,
        permissions = setOf(Permission.READ, Permission.UPDATE, Permission.DELETE),
        state = QuestionState.MODERATED,
        visibility = QuestionVisibility.PUBLIC
    )

    val errorMessage = "Stub error message."

    fun allQuestions() = mutableListOf(questionA, questionB)
}
