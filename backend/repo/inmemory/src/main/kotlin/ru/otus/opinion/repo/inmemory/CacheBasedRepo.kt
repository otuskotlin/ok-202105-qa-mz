package ru.otus.opinion.repo.inmemory

import kotlinx.coroutines.flow.*
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import ru.otus.opinion.models.Question
import ru.otus.opinion.repo.api.*
import java.time.Duration

class CacheBasedRepo(
    private val ttl: Duration = Duration.ofMinutes(1)
) : Repo {

    private val cache: Cache<String, Question> = let {
        val cacheManager: CacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .build(true)

        cacheManager.createCache(
            "questions",
            CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                    String::class.java,
                    Question::class.java,
                    ResourcePoolsBuilder.heap(100)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(ttl))
                .build()
        )
    }


    override suspend fun save(saveRequest: SaveRequest): SaveResponse {
        val question = saveRequest.question
        val id = question.questionId.id
        if (id.isBlank()) {
            return SaveResponse("Can not save question without ID.")
        }
        cache.put(id, question)
        return SaveResponse(content = question)
    }

    /**
     * TODO: support pagination direction
     */
    override suspend fun list(listRequest: ListRequest): ListResponse {
        val pagination = listRequest.pagination
        val sinceId = pagination.id
        val questions = cache.asFlow()
            .filter { it.key > sinceId }
            .take(pagination.count)
            .map { it.value }
            .toList()
        return ListResponse(content = questions)
    }

}