package ru.otus.opinion.repo.cassandra.dao

import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import ru.otus.opinion.models.Pagination
import ru.otus.opinion.repo.cassandra.dto.QuestionDto
import ru.otus.opinion.repo.cassandra.schema.SchemaInitializer
import java.lang.IllegalStateException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer

class SearchQueryProvider(
    private val context: MapperContext,
    private val entityHelper: EntityHelper<QuestionDto>
) {
    fun list(pagination: Pagination): CompletionStage<Collection<QuestionDto>> {
        var select = entityHelper.selectStart().allowFiltering()
        // TODO implement pagination.direction support
        select = select
            .whereColumn(SchemaInitializer.ID_COLUMN)
            .isGreaterThan(QueryBuilder.literal(pagination.id))
        select = select.limit(pagination.count)

        val resultMapper = ResultMapper(entityHelper)
        context.session
            .executeAsync(select.build())
            .whenComplete(resultMapper)
        return resultMapper.getResult()
    }

    inner class ResultMapper(private val entityHelper: EntityHelper<QuestionDto>) : BiConsumer<AsyncResultSet?, Throwable?> {
        private val dtos = mutableListOf<QuestionDto>()
        private val future = CompletableFuture<Collection<QuestionDto>>()

        override fun accept(resultSet: AsyncResultSet?, exception: Throwable?) {
            when {
                exception != null -> future.completeExceptionally(exception)
                resultSet == null -> future.completeExceptionally(IllegalStateException("Result set should not be null."))
                else -> {
                    dtos.addAll(resultSet.currentPage().map { entityHelper.get(it, true) })
                    if (resultSet.hasMorePages())
                        resultSet.fetchNextPage().whenComplete(this)
                    else
                        future.complete(dtos)
                }
            }
        }

        fun getResult() = future
    }
}