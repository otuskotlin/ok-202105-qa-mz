package ru.otus.opinion.logging

import ch.qos.logback.classic.Logger
import net.logstash.logback.argument.StructuredArguments
import org.slf4j.Marker
import org.slf4j.event.Level
import org.slf4j.event.LoggingEvent
import java.lang.System.currentTimeMillis
import java.time.Instant

class CoreLogger(val logger: Logger, val loggerId: String = "") {

    fun log(
        msg: String = "",
        level: Level = Level.TRACE,
        e: Throwable? = null,
        data: Any? = null,
        vararg objs: Pair<String, Any>?
    ) {
        logger.log(object : LoggingEvent {
            override fun getThrowable() = e
            override fun getTimeStamp(): Long = Instant.now().toEpochMilli()
            override fun getThreadName(): String = Thread.currentThread().name
            override fun getMessage(): String = msg
            override fun getMarker(): Marker? = null
            override fun getArgumentArray(): Array<out Any> = data?.let { d ->
                arrayOf(
                    *objs.map { StructuredArguments.keyValue(it?.first, it?.second) }.toTypedArray(),
                    StructuredArguments.keyValue("data", d),
                ).filterNotNull().toTypedArray()
            } ?: objs.mapNotNull { StructuredArguments.keyValue(it?.first, it?.second) }.toTypedArray()

            override fun getLevel(): Level = level
            override fun getLoggerName(): String = logger.name
        })
    }

    suspend fun <T> logAction(
        actionId: String = "",
        level: Level = Level.INFO,
        action: suspend () -> T,
        contextConverter: (T) -> Any? = {}
    ): T = try {
        val startMillis = currentTimeMillis()
        log("$loggerId $actionId started", level)
        action().also {
            log(
                "$loggerId $actionId finished",
                level,
                data = contextConverter(it),
                objs = arrayOf(Pair("elapsedMillis", currentTimeMillis() - startMillis))
            )
        }
    } catch (ex: Throwable) {
        log("$loggerId $actionId failed", Level.ERROR, ex)
        throw ex
    }

    suspend fun <T> doWithErrorLogging(
        id: String = "",
        throwRequired: Boolean = true,
        block: suspend () -> T,
    ): T? = try {
        block()
    } catch (e: Throwable) {
        log("$loggerId $id failed", Level.ERROR, e)
        if (throwRequired) throw e else null
    }
}