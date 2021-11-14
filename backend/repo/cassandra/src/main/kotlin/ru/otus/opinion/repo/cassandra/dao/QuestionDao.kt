package ru.otus.opinion.repo.cassandra.dao

import com.datastax.oss.driver.api.mapper.annotations.*
import ru.otus.opinion.models.Pagination
import ru.otus.opinion.repo.cassandra.dto.QuestionDto
import java.util.concurrent.CompletionStage

@Dao
interface QuestionDao {
    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    fun create(dto: QuestionDto): CompletionStage<Unit>

    @Select
    fun read(id: String): CompletionStage<QuestionDto?>

    @QueryProvider(providerClass = SearchQueryProvider::class, entityHelpers = [QuestionDto::class])
    fun list(pagination: Pagination): CompletionStage<Collection<QuestionDto>>
}