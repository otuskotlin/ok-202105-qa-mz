package ru.otus.opinion.ktor.plugins

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import ru.otus.opinion.ktor.configs.AppConfig
import ru.otus.opinion.ktor.controllers.QuestionController
import ru.otus.opinion.ktor.controllers.QuestionControllerImpl

fun Application.configureRouting(appConfig: AppConfig, httpClient: HttpClient) {
    val questionService = appConfig.service
    val questionController: QuestionController = QuestionControllerImpl(questionService)

    routing {
        static {
            resources("web/static")
            resources("web/images")
            resources("web/dist")
            resources("web/css")
            /* spa on vue */
            resource("create_question", "web/dist/index.html")
            defaultResource("web/static/index.html")
        }

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

        authenticate("oauth") {
            get("/login") {
            }
            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                val token = principal?.accessToken.toString()
                call.sessions.set(UserSession(token))
                log.info("Token")
                log.info(token)
                call.respondRedirect("/welcome")
            }
        }

        get("/welcome") {
            val userSession: UserSession? = call.sessions.get<UserSession>()
            log.info("UserSession token")
            log.info(userSession?.token)
            if (userSession != null) {
                val userInfo: UserInfo = httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
                    }
                }
                val jsonStr = jacksonObjectMapper().writeValueAsString(userInfo)
                call.respond(FreeMarkerContent("welcome.ftlh",
                    mapOf("userName" to userInfo.familyName, "info" to jsonStr), ""))
            } else {
                call.respondRedirect("/")
            }
        }
    }
}

data class UserSession(val token: String)

@JsonSerialize
@JsonDeserialize
data class UserInfo(
    val id: String,
    val name: String,
    @JsonProperty("given_name") val givenName: String,
    @JsonProperty("family_name") val familyName: String,
    val picture: String,
    val locale: String
)
