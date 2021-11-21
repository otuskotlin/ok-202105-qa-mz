package ru.otus.opinion.ktor.plugins

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.opinion.ktor.configs.AppConfig
import ru.otus.opinion.ktor.controllers.QuestionController
import ru.otus.opinion.ktor.controllers.QuestionControllerImpl

fun Application.configureRouting(appConfig: AppConfig) {
    val questionService = appConfig.service
    val questionController: QuestionController = QuestionControllerImpl(questionService)

    routing {
        get("/") {
            call.respond(FreeMarkerContent(
                "index.ftl",
                mapOf("questions" to questionController.landingPageData()),
                ""))
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
