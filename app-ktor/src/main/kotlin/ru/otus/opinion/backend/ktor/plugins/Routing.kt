package ru.otus.opinion.backend.ktor.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.opinion.backend.ktor.controllers.QuestionController
import ru.otus.opinion.bakend.services.QuestionService

fun Application.configureRouting() {

    val questionService = QuestionService()
    val questionController = QuestionController(questionService)

    routing {
        get("/") {
            call.respondText("Hello, World!")
        }

        route("question") {
            post("list") {
                questionController.list(call)
            }
            post("create") {
                questionController.create(call)
            }
        }
    }
}
