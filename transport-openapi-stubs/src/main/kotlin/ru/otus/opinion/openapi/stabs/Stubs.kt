package ru.otus.opinion.openapi.stabs

import ru.otus.opinion.openapi.models.*

object Stubs {
    const val questionId = "Stub question id"

    val questionA = Question(
        questionId = questionId,
        title = "Ultimate question.",
        content = "What do you get if you multiply six by nine?",
        author = "James Casingworthy",
        creationTime = "2021-09-13T19:11:07Z",
        language = "eng",
        tags = listOf("philosophy"),
        state = QuestionState.MODERATED,
        visibility = Visibility.PUBLIC,
        likesCount = 111,
        answersCount = 1,
        permissions = setOf(Permission.READ, Permission.UPDATE, Permission.DELETE)
    )

    val createRequestA = CreateQuestionRequest(
        requestId = "123",
        processingMode = ProcessingMode.STUB,
        question = questionA,
        stub = Stub.SUCCESS
    )

    val questionB = Question(
        questionId = "17",
        title = "Funny question.",
        content = "To beat or not to beat?",
        author = "Mary",
        creationTime = "2021-08-07T14:04:01Z",
        language = "eng",
        tags = listOf("humor"),
        likesCount = 117,
        answersCount = 3,
        permissions = setOf(Permission.READ, Permission.UPDATE, Permission.DELETE),
        state = QuestionState.MODERATED,
        visibility = Visibility.PUBLIC
    )

    val questionsRequest = QuestionsRequest(
        requestId = "123",
        processingMode = ProcessingMode.STUB,
        stub = Stub.SUCCESS,
        pagination = Pagination(
            objectsCount = 10,
            objectId = "0",
            relation = Pagination.Relation.AFTER
        )
    )

    val allQuestions = listOf(questionA, questionB)
}