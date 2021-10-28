package ru.otus.opinion.ktor

import io.ktor.application.*
import ru.otus.opinion.ktor.plugins.configureHTTP
import ru.otus.opinion.ktor.plugins.configureRouting

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    println("Test mode: $testing")
    configureHTTP()
    configureRouting()
}

fun Application.testModule() = this.module(true)
