package ru.otus.opinion.repo.cassandra.dto.producers

import com.datastax.oss.driver.api.core.cql.Statement
import com.datastax.oss.driver.api.core.type.reflect.GenericType
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.mapper.result.MapperResultProducer
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

/**
 * Required to return CompletionStage<Unit> from [ru.otus.opinion.repo.cassandra.dao.QuestionDao] methods,
 * because mapper library does not know the Kotlin [Unit] type.
 *
 * Also we can use [Void] instead of [Unit], and then this producer will not be required.
 */
class CompletionStageOfUnitProducer : MapperResultProducer {
    companion object {
        val PRODUCED_TYPE = object : GenericType<CompletionStage<Unit>>() {}
    }

    override fun canProduce(resultType: GenericType<*>): Boolean =
        resultType == PRODUCED_TYPE

    override fun execute(
        statement: Statement<*>,
        context: MapperContext,
        entityHelper: EntityHelper<*>?
    ): CompletionStage<Unit> {
        val result = CompletableFuture<Unit>()
        context.session.executeAsync(statement).whenComplete { _, e ->
            if (e != null) result.completeExceptionally(e)
            else result.complete(Unit)
        }
        return result
    }

    override fun wrapError(e: Exception): CompletionStage<Unit> =
        CompletableFutures.failedFuture(e)
}