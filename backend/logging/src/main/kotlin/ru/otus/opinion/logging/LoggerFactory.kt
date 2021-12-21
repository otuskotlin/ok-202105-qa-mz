package ru.otus.opinion.logging

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory

fun coreLogger(loggerId: String): CoreLogger = coreLogger(
    logger = LoggerFactory.getLogger(loggerId) as Logger
)

fun coreLogger(cls: Class<out Any>): CoreLogger = coreLogger(
    logger = LoggerFactory.getLogger(cls) as Logger
)

/**
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun coreLogger(logger: Logger): CoreLogger = CoreLogger(
    logger = logger,
    loggerId = logger.name
)
