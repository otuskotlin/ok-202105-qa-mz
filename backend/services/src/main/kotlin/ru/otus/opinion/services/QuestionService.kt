package ru.otus.opinion.services

import ru.otus.opinion.context.IRequestContext
import ru.otus.opinion.context.RequestContext

interface QuestionService {
    suspend fun handle(ctx: RequestContext): IRequestContext

    companion object {
        fun getService() = QuestionServiceImpl(
            config = ServiceConfig.getServiceConfig()
        )
        fun getTestService() = QuestionServiceImpl(
            config = ServiceConfig.getTestServiceConfig()
        )
    }
}