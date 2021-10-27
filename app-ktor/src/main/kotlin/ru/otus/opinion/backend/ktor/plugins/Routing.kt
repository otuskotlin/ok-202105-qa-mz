package ru.otus.opinion.backend.ktor.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.opinion.backend.ktor.controllers.QuestionController
import ru.otus.opinion.backend.ktor.controllers.QuestionControllerImpl
import ru.otus.opinion.bakend.services.QuestionService
import ru.otus.opinion.bakend.services.QuestionServiceImpl
import ru.otus.opinion.logics.Crud

fun Application.configureRouting() {
    val crud = Crud()
    val questionService: QuestionService = QuestionServiceImpl(crud)
    val questionController: QuestionController = QuestionControllerImpl(questionService)

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
