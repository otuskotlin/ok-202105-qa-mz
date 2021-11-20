package ru.otus.opinion.repo.cassandra

import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory
import ru.otus.opinion.models.ErrorLevel
import ru.otus.opinion.models.ErrorType
import ru.otus.opinion.models.QuestionId
import ru.otus.opinion.models.ServerError
import ru.otus.opinion.repo.api.*
import ru.otus.opinion.repo.cassandra.dao.QuestionDao
import ru.otus.opinion.repo.cassandra.dto.QuestionDto
import java.util.*

class CassandraRepo(
    private val dao: QuestionDao,
    private val timeoutMillis: Long = 30_000
) : Repo {
    private val log = LoggerFactory.getLogger(javaClass)

    override suspend fun create(createRequest: CreateRequest): CreateResponse {
        val newQuestion = createRequest.question.copy(
            questionId = QuestionId(id = UUID.randomUUID().toString())
        )
        return try {
            withTimeout(timeoutMillis) {
                dao.create(QuestionDto(newQuestion)).toCompletableFuture().join()
            }
            CreateResponse(content = newQuestion)
        } catch(ex: Throwable) {
            log.error("Failed to create question $newQuestion", ex)
            CreateResponse(ex.localizedMessage)
        }
    }

    override suspend fun list(listRequest: ListRequest): ListResponse {
        return try {
            val dtos = withTimeout(timeoutMillis) {
                dao.list(listRequest.pagination).toCompletableFuture().join()
            }
            ListResponse(content = dtos.map(QuestionDto::toModel))
        } catch (ex: Throwable) {
            log.error("Failed to read questions for ${listRequest.pagination}", ex)
            ListResponse(ServerError(message = ex.localizedMessage, level = ErrorLevel.ERROR, errorType = ErrorType.DB_ERROR))
        }
    }
}