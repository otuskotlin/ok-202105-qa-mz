package ru.otus.backend.ktor.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.opinion.backend.ktor.module
import ru.otus.opinion.openapi.models.Request
import kotlin.test.assertEquals

abstract class RouterTest {
    val mapper = jacksonObjectMapper()

    protected inline fun <reified T> testPostRequest(
        body: Request? = null,
        path: String,
        crossinline block: T.() -> Unit
    ) {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Post, path) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                setBody(mapper.writeValueAsString(body))
            }.apply {
                println(response.content)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                mapper.readValue(response.content, T::class.java).block()
            }
        }
    }
}