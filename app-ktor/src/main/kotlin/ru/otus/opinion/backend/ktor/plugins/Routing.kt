package ru.otus.opinion.backend.ktor.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.otus.opinion.openapi.models.CreateQuestionRequest

fun Application.configureRouting() {
    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("question") {
            post("list") {}
            post("create") {
                val data = call.receive<CreateQuestionRequest>()
                println(data)
                call.respondText { data.toString() }
            }
        }
    }

}
