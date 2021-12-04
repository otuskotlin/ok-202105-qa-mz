package ru.otus.opinion.ktor.plugins

import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.opinion.ktor.configs.AppConfig
import ru.otus.opinion.ktor.controllers.QuestionController
import ru.otus.opinion.ktor.controllers.QuestionControllerImpl

fun Application.configureRouting(appConfig: AppConfig) {
    val questionService = appConfig.service
    val questionController: QuestionController = QuestionControllerImpl(questionService)

    routing {
        // it works
        static {
            resources("web/static")
            resources("web/images")
            resources("web/dist")
            resources("web/css")
            /* spa on vue */
            resource("create_question", "web/dist/index.html")
            defaultResource("web/static/index.html")
        }

//        get("/") {
//            call.application.environment.log.info("Landing page requested.")
//            call.respond("/web/static/index.html")
//            call.respondTemplate("index.html", mapOf("questions" to questionController.landingPageData()))
//        }

        route("question") {
            get("list") {
                call.respond(FreeMarkerContent(
                    "questions_list.ftl",
                    mapOf("questions" to questionController.defaultQuestions()),
                    ""))
            }
            post("list") {
                questionController.list(call)
            }
            post("create") {
                questionController.create(call)
            }
        }
    }
}
