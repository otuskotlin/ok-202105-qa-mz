package ru.otus.backend.ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.opinion.backend.ktor.module
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun  testRoot() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello, World!", response.content)
            }
        }
    }
}