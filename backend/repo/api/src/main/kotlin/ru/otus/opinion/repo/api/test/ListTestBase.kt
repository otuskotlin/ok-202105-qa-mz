package ru.otus.opinion.repo.api.test

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.opinion.models.Pagination
import ru.otus.opinion.models.Question
import ru.otus.opinion.models.Relation
import ru.otus.opinion.models.stubs.QuestionStubs
import ru.otus.opinion.repo.api.ListRequest
import ru.otus.opinion.repo.api.Repo
import ru.otus.opinion.repo.api.CreateRequest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class ListTestBase {
    abstract val repo: Repo

    @Test
    fun listSuccess() {
        val questions = QuestionStubs.allQuestions()
        insertTestData(questions)
        val sortedIds = questions
            .map { it.questionId }
            .sortedBy { it.id }
            .toList()
        val firstId = sortedIds[0]
        val expectedSize = sortedIds.size - 1
        val pagination = Pagination(count = expectedSize, id = firstId.id, relation = Relation.AFTER)

        val response = runBlocking { repo.list(ListRequest(pagination)) }

        assertTrue(response.isSuccess)
        assertTrue(response.errors.isEmpty())
        assertEquals(expectedSize, response.content.size)
    }

    private fun insertTestData(questions: MutableList<Question>) {
        runBlocking {
            questions.forEach {
                    question -> repo.create(CreateRequest(question))
            }
        }
    }

}