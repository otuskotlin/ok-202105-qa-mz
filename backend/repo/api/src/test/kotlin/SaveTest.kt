import kotlinx.coroutines.runBlocking
import ru.otus.opinion.models.stubs.QuestionStubs
import ru.otus.opinion.repo.api.Repo
import ru.otus.opinion.repo.api.SaveRequest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class SaveTest {
    abstract val repo: Repo

    fun saveSuccess() {
        val question = QuestionStubs.questionA
        val saveRequest = SaveRequest(question)
        val response = runBlocking { repo.save(saveRequest) }
        assertTrue(response.isSuccess)
        assertTrue(response.errors.isEmpty())
        assertEquals(question, response.content)
    }
}