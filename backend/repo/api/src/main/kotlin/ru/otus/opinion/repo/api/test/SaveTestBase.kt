package ru.otus.opinion.repo.api.test

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.opinion.models.stubs.QuestionStubs
import ru.otus.opinion.repo.api.Repo
import ru.otus.opinion.repo.api.SaveRequest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class SaveTestBase {
    abstract val repo: Repo

    @Test
    fun saveSuccess() {
        val question = QuestionStubs.questionA
        val saveRequest = SaveRequest(question)
        val response = runBlocking { repo.save(saveRequest) }
        assertTrue(response.isSuccess)
        assertTrue(response.errors.isEmpty())
        assertEquals(question, response.content)
    }
}