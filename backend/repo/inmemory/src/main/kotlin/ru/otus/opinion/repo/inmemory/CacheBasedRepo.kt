package ru.otus.opinion.repo.inmemory

import kotlinx.coroutines.flow.*
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import ru.otus.opinion.models.Language
import ru.otus.opinion.models.Question
import ru.otus.opinion.models.QuestionId
import ru.otus.opinion.models.UserId
import ru.otus.opinion.repo.api.*
import java.time.Duration
import java.util.*

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


    override suspend fun create(createRequest: CreateRequest): CreateResponse {
        val id = UUID.randomUUID().toString()
        val question = createRequest.question.copy(questionId = QuestionId(id = id))
        cache.put(id, question)
        return CreateResponse(content = question)
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