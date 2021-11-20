package ru.otus.opinion.repo.api.test

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.opinion.models.stubs.QuestionStubs
import ru.otus.opinion.repo.api.Repo
import ru.otus.opinion.repo.api.CreateRequest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

abstract class CreateTestBase {
    abstract val repo: Repo

    @Test
    fun saveSuccess() {
        val question = QuestionStubs.questionA
        val saveRequest = CreateRequest(question)
        val response = runBlocking { repo.create(saveRequest) }
        assertTrue(response.isSuccess)
        assertTrue(response.errors.isEmpty())
        val responseQuestion = response.content
        assertNotNull(responseQuestion)
        responseQuestion.questionId = question.questionId
        assertEquals(question, response.content)
    }
}