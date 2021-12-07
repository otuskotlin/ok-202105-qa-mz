package ru.otus.opinion.ktor.plugins

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.sessions.*
import ru.otus.opinion.ktor.configs.AppConfig

fun Application.configureAuthentication(appConfig: AppConfig, httpClient: HttpClient) {

    install(Sessions) {
        cookie<UserSession>("user_session")
    }

    install(Authentication) {

        oauth("oauth") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://oauth2.googleapis.com/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = httpClient
        }

    }
}