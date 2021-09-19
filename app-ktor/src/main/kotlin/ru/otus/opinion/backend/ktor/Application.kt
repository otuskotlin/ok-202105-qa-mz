package ru.otus.opinion.backend.ktor

import io.ktor.application.*
import ru.otus.opinion.backend.ktor.plugins.*

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
