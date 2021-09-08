package ru.otus.models.stabs

import ru.otus.opinion.backend.common.models.*
import java.time.Instant

object QuestionStubs {
    val mainQueston = Question(
        questionId = "321",
        title = "Ultimate question.",
        content = "What do you get if you multiply six by nine?",
        author = UserId("James Casingworthy"),
        creationTime = Instant.parse("2021-08-07T13:04:01Z"),
        language = Language("eng"),
        tags = listOf(QuestionTag("philosophy")),
        likesCount = 111,
        answersCount = 1,
        permissions = setOf(Permission.READ, Permission.UPDATE, Permission.DELETE),
        state = QuestionState.ACCEPTED,
        visibility = QuestionVisibility.REGISTERED_ONLY
    )

    val funnyQuestion = Question(
        questionId = "17",
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

    fun allQuestions() = mutableListOf<Question>(mainQueston, funnyQuestion)
}
