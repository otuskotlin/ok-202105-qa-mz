package ru.otus.opinion.ktor.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.opinion.ktor.configs.AppConfig
import ru.otus.opinion.services.QuestionService
import ru.otus.opinion.ktor.controllers.QuestionController
import ru.otus.opinion.ktor.controllers.QuestionControllerImpl

fun Application.configureRouting(appConfig: AppConfig) {
    val questionService = appConfig.service
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
