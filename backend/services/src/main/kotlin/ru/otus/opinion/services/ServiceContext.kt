package ru.otus.opinion.services

import ru.otus.opinion.context.IRequestContext
import ru.otus.opinion.context.RequestContext

data class ServiceContext(
    val serviceConfig: ServiceConfig,
    val requestContext: RequestContext
) : IRequestContext by requestContext