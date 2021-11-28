package ru.otus.opinion.ktor

import io.ktor.application.*
import org.slf4j.LoggerFactory
import ru.otus.opinion.ktor.configs.AppConfig
import ru.otus.opinion.ktor.plugins.configureHTTP
import ru.otus.opinion.ktor.plugins.configureRouting

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@JvmOverloads
fun Application.module(appConfig: AppConfig = AppConfig(environment)) {
    val port = environment.config.propertyOrNull("ktor.deployment.port")?.getString() ?: "8080"
    val env = environment.config.propertyOrNull("ktor.environment")?.getString()
    log.info("Listening http request on port {} in {} mode.", port, env)
    configureHTTP()
    configureRouting(appConfig)
}

//fun Application.module2() {}